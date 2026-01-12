package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private Long id;
    private QuestionContent content;
    private QuestionState state;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.content = new QuestionContent(title, contents);
        this.state = new QuestionState(writer);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return content.getTitle();
    }

    public Question setTitle(String title) {
        content.setTitle(title);
        return this;
    }

    public String getContents() {
        return content.getContents();
    }

    public Question setContents(String contents) {
        content.setContents(contents);
        return this;
    }

    public NsUser getWriter() {
        return state.getWriter();
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        state.addAnswer(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return state.isOwner(loginUser);
    }

    public Question setDeleted(boolean deleted) {
        state.setDeleted(deleted);
        return this;
    }

    public boolean isDeleted() {
        return state.isDeleted();
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + getTitle() + ", contents=" + getContents() + ", writer=" + getWriter() + "]";
    }

    public List<DeleteHistory> deleteBy(NsUser loginUser, LocalDateTime now)
        throws CannotDeleteException {

        state.validateDeletePermission(loginUser);

        state.setDeleted(true);

        List<DeleteHistory> histories = new ArrayList<>();
        histories.add(new DeleteHistory(ContentType.QUESTION, this.id, state.getWriter(), now));

        state.getAnswers().deleteAll();
        histories.addAll(state.getAnswers().createDeleteHistories(now));

        return histories;
    }
}
