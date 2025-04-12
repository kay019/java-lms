package nextstep.qna.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Answer  extends BaseDomain {

    private final NsUser writer;

    private final String contents;

    private Question question;

    public Answer(Long id, NsUser writer, Question question, String contents) {
        this.id = id;
        if (writer == null) {
            throw new UnAuthorizedException();
        }

        if (question == null) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(NsUser writer) {
        return this.writer.equals(writer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public DeleteHistory delete() {
        this.deleted = true;
        this.updatedAt = LocalDateTime.now();
        return new DeleteHistory(ContentType.ANSWER, this.id, this.writer, LocalDateTime.now());
    }

    public void link(Question question) {
        this.question = question;
        question.addAnswer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return deleted == answer.deleted &&
            Objects.equals(id, answer.id) &&
            Objects.equals(writer, answer.writer) &&
            Objects.equals(question, answer.question) &&
            Objects.equals(contents, answer.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writer, question, contents);
    }
}
