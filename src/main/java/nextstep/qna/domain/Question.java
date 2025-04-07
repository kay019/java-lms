package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Question {
    private Long id;

    private String title;

    private String contents;

    private NsUser writer;

    private final Answers answers = new Answers();

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void delete(NsUser loginUser) throws CannotDeleteException {
        checkUser(loginUser);
        this.deleted = true;
        answers.delete();
    }

    public DeleteHistories deleteHistories() {
        DeleteHistories deleteHistories = answers.deleteHistories();
        deleteHistories.add(deleteHistory());
        return deleteHistories;
    }

    private DeleteHistory deleteHistory() {
        if (deleted) {
            return new DeleteHistory(ContentType.QUESTION, this.id, this.writer, LocalDateTime.now());
        }
        return null;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    private void checkUser(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        if (!isAnswersOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    private boolean isAnswersOwner(NsUser loginUser) {
        return answers.isOwner(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }
}
