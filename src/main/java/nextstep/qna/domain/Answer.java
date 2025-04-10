package nextstep.qna.domain;

import java.time.LocalDateTime;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import static nextstep.qna.domain.ContentType.ANSWER;

public class Answer {
    private Long id;

    private NsUser writer;

    private String contents;

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Answer() {
    }

    public Answer(NsUser writer, String contents) {
        this(null, writer, contents);
    }

    public Answer(Long id, NsUser writer, String contents) {
        this.id = id;
        if(writer == null) {
            throw new UnAuthorizedException();
        }

        this.writer = writer;
        this.contents = contents;
    }

    public DeleteHistory delete(NsUser loginUser) throws CannotDeleteException {
        validate(loginUser);

        this.deleted = true;
        return new DeleteHistory(ANSWER, id, writer, LocalDateTime.now());
    }

    private void validate(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(NsUser writer) {
        return this.writer.equalsNameAndEmail(writer);
    }

    public NsUser getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + writer + ", contents=" + contents + "]";
    }
}
