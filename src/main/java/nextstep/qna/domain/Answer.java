package nextstep.qna.domain;

import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class Answer {

    private final Long id;
    private final NsUser writer;
    private final String contents;
    private final Metadata metadata = new Metadata();
    private Question question;

    public Answer(NsUser writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

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

    public void delete() {
        metadata.delete();
    }

    public DeleteHistory createDeleteHistory() {
        return DeleteHistory.fromAnswer(id, writer, metadata.getDeletedAt());
    }

    public boolean isDeleted() {
        return metadata.isDeleted();
    }

    public boolean isOwner(NsUser writer) {
        return this.writer.equals(writer);
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer [id=" + id + ", writer=" + writer + ", contents=" + contents + "]";
    }
}
