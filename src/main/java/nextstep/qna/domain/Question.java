package nextstep.qna.domain;

import java.time.LocalDateTime;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Question {

    private final Answers answers = new Answers();
    private final LocalDateTime createdDate = LocalDateTime.now();
    private final Long id;
    private final String title;
    private final String contents;
    private final NsUser writer;
    private boolean deleted = false;
    private LocalDateTime updatedDate;
    private DeleteHistory deleteHistory;

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isAllAnswersDeleted() {
        return answers.isAllDeleted();
    }

    public void delete(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        answers.deleteAll(loginUser);
        
        deleted = true;
        deleteHistory = new DeleteHistory(ContentType.QUESTION, id, writer, LocalDateTime.now());
    }

    public DeleteHistories getDeleteHistories() {
        DeleteHistories deleteHistories = new DeleteHistories();
        deleteHistories.add(deleteHistory);
        deleteHistories.addAll(answers.getDeleteHistories());
        return deleteHistories;
    }

    private boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
