package nextstep.qna.domain;

import nextstep.qna.exception.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Question {
    private Long id;

    private String title;

    private String contents;

    private NsUser writer;

    private final List<Answer> answers = new ArrayList<>();

    private boolean deleted = false;

    public Question() {
    }

    public Question(final NsUser writer, final String title, final String contents) {
        this(0L, writer, title, contents);
    }

    public Question(final Long id, final NsUser writer, final String title, final String contents) {
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

    public void addAnswer(final Answer answer) {
        answers.add(answer);
    }

    public boolean isOwner(final NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
    
    public List<DeleteHistory> delete(final NsUser loginUser) throws CannotDeleteException {
        validateDeletionPermission(loginUser);
        this.deleted = true;
        return createDeleteHistories(loginUser);
    }

    private void validateDeletionPermission(final NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        if (hasAnswerFromOtherUser(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private boolean hasAnswerFromOtherUser(final NsUser loginUser) {
        return answers.stream()
                .anyMatch(answer -> !answer.isOwner(loginUser));
    }

    private List<DeleteHistory> createDeleteHistories(final NsUser loginUser) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(createQuestionDeleteHistory());
        deleteHistories.addAll(deleteAnswers(loginUser));
        return deleteHistories;
    }

    private DeleteHistory createQuestionDeleteHistory() {
        return new DeleteHistory(ContentType.QUESTION, this.getId(), this.getWriter(), LocalDateTime.now());
    }

    private List<DeleteHistory> deleteAnswers(final NsUser loginUser) {
        return answers.stream()
                .map(answer -> answer.delete(loginUser))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
