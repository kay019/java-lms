package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Question {
    private Long id;

    private String title;

    private String contents;

    private NsUser writer;

    private Answers answers = new Answers();

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        this.answers = this.answers.addAnswer(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(NsUser loginUser) {
        checkOwner(loginUser);
        checkAnswers(loginUser);
        this.deleted = true;
        answers.deleteAll();
    }

    private void checkOwner(NsUser loginUser) {
        if (!writer.equals(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private void checkAnswers(NsUser loginUser) {
        answers.checkOwners(loginUser);
    }

    public List<DeleteHistory> createDeleteHistory() {
        List<DeleteHistory> histories = new ArrayList<>();
        histories.add(createQuestionHistory());
        histories.addAll(createAnswersHistory());
        return histories;
    }

    private DeleteHistory createQuestionHistory() {
        return new DeleteHistory(ContentType.QUESTION, this.id, this.writer,
                LocalDateTime.now());
    }

    private List<DeleteHistory> createAnswersHistory() {
        return answers.createHistories();
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
