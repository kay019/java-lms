package nextstep.qna.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Question extends BaseDomain {

    private final String title;

    private final String contents;

    private final NsUser writer;

    public Question(Long id, NsUser writer, String title, String contents) {
        super(id);
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isNotOwner(NsUser loginUser) {
        return !writer.equals(loginUser);
    }

    public DeleteHistory delete(NsUser loginUser) throws CannotDeleteException {
        if (isNotOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        this.deleted = true;
        this.updatedAt = LocalDateTime.now();

        return new DeleteHistory(ContentType.QUESTION, id, writer, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return Objects.equals(title, question.title) && Objects.equals(contents, question.contents) && Objects.equals(writer, question.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, contents, writer);
    }

    @Override
    public String toString() {
        return "Question{" +
            "title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", writer=" + writer +
            '}';
    }
}
