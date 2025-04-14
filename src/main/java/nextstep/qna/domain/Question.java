package nextstep.qna.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question extends BaseDomain {

    private final String title;

    private final String contents;

    private final NsUser writer;

    private final Answers answers;

    public Question(Long id, NsUser writer, String title, String contents) {
        super(id);
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.answers = new Answers();
    }

    public void addAnswer(Answer answer) {
        answer.link(this);
        answers.add(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isNotOwner(NsUser loginUser) {
        return !writer.equals(loginUser);
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        if (isNotOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        this.deleted = true;
        this.updatedAt = LocalDateTime.now();

        List<DeleteHistory> res = new ArrayList<>(
            List.of(new DeleteHistory(ContentType.QUESTION, id, writer, LocalDateTime.now()))
        );
        res.addAll(answers.delete(loginUser));

        return res;
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
        return Objects.hash(super.hashCode(), title, contents, writer, answers);
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
