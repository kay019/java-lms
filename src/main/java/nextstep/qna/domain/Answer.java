package nextstep.qna.domain;

import java.time.LocalDateTime;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

public class Answer {

    private final Long id;

    private final NsUser writer;
    private final String contents;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private Question question;
    private boolean deleted = false;
    private LocalDateTime updatedDate;

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

    public Long getId() {
        return id;
    }

    public void delete() {
        this.deleted = true;
    }

    public DeleteHistories createDeleteHistories() {
        if (!isDeleted()) {
            return new DeleteHistories();
        }
        
        DeleteHistories histories = new DeleteHistories();
        histories.add(new DeleteHistory(ContentType.ANSWER, id, writer, LocalDateTime.now()));
        return histories;
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
