package nextstep.qna.domain;

import nextstep.qna.exception.NotFoundException;
import nextstep.qna.exception.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Answer {
    private Long id;
    private NsUser writer;
    private Question question;
    private String contents;
    private boolean deleted = false;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    public Answer(Long id, NsUser writer, Question question, String contents) {
        validate(writer, question);
        this.id = id;
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.question.addAnswer(this);
    }

    private static void validate(NsUser writer, Question question) {
        if(writer == null) {
            throw new UnAuthorizedException();
        }
        if(question == null) {
            throw new NotFoundException();
        }
    }

    public DeleteHistory delete() {
        this.deleted = true;
        return createDeleteHistory();
    }

    public DeleteHistory createDeleteHistory() {
        return new DeleteHistory(ContentType.ANSWER, id, writer);
    }

    public boolean isDeleted() {
        return deleted;
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
