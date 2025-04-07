package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(NsUser loginUser) throws CannotDeleteException {
        checkDeletableByUser(loginUser);
        delete();
    }

    public List<DeleteHistory> toDeleteHistory() {
        List<DeleteHistory> res = new ArrayList<>(
            List.of(new DeleteHistory(ContentType.QUESTION, id, writer, LocalDateTime.now()))
        );

        res.addAll(answers.stream().map(Answer::toDeleteHistory).collect(Collectors.toList()));
        return res;
    }

    void checkDeletableByUser(NsUser loginUser) throws CannotDeleteException {
        if (isNotOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        if (isNotAllAnswersWrittenByUser(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    void delete() {
        this.deleted = true;
        answers.forEach(Answer::delete);
    }

    private boolean isNotOwner(NsUser loginUser) {
        return !writer.equals(loginUser);
    }

    private boolean isNotAllAnswersWrittenByUser(NsUser loginUser) {
        return !answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return deleted == question.deleted &&
            Objects.equals(id, question.id) &&
            Objects.equals(title, question.title) &&
            Objects.equals(contents, question.contents) &&
            Objects.equals(writer, question.writer) &&
            Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, contents, writer, answers, deleted, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
