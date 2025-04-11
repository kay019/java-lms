package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Question {
    private final QuestionInfo questionInfo;

    private Answers answers = new Answers();

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question(NsUser writer, String title, String contents) {
        this(new QuestionInfo(0L, writer, title, contents));
    }

    public Question(QuestionInfo questionInfo) {
        this.questionInfo = questionInfo;
    }

    public Long getId() {
        return questionInfo.getId();
    }

    public String getTitle() {
        return questionInfo.getTitle();
    }

    public String getContents() {
        return questionInfo.getContents();
    }

    public NsUser getWriter() {
            return questionInfo.getWriter();
    }

    public void addAnswer(Answer answer) {
        this.answers = answers.withAddedAnswer(answer);
    }

    public List<DeleteHistory> delete(NsUser loginUser, long questionId) {
        validate(loginUser);

        this.deleted = true;

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, questionId, questionInfo.getWriter(), LocalDateTime.now()));
        deleteHistories.addAll(answers.deleteAll(loginUser));
        return deleteHistories;
    }

    private void validate(NsUser loginUser) {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    public boolean isOwner(NsUser loginUser) {
        return questionInfo.getWriter().equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return questionInfo.toString();
    }
}
