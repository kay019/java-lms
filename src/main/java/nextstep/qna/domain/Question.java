package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
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

    private List<Answer> answers = new ArrayList<>();

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

    public List<DeleteHistory> delete(NsUser loginUser) {
        valid(loginUser);
        setDeleted(true);

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, this.id, this.writer, LocalDateTime.now()));

        deleteHistories.addAll(answers.stream()
                .map(a -> a.delete(loginUser))
                .collect(Collectors.toList()));
        return deleteHistories;
    }

    private void valid(NsUser loginUser) {
        if (isDifferentUser(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private boolean isDifferentUser(NsUser loginUser) {
        return !writer.equals(loginUser);
    }

    public Long getId() {
        return id;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
