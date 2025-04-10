package nextstep.qna.domain;

import java.time.LocalDateTime;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import static nextstep.qna.domain.ContentType.ANSWER;

public class Answer {
    private final AnswerInfo answerInfo;

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Answer(NsUser writer, String contents) {
        this(new AnswerInfo(null, writer, contents));
    }

    public Answer(AnswerInfo answerInfo) {
        this.answerInfo = answerInfo;
    }

    public DeleteHistory delete(NsUser loginUser) throws CannotDeleteException {
        validate(loginUser);

        this.deleted = true;
        return new DeleteHistory(ANSWER, answerInfo.getId(), answerInfo.getWriter(), LocalDateTime.now());
    }

    private void validate(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    public Long getId() {
        return answerInfo.getId();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(NsUser writer) {
        return this.answerInfo.getWriter().equalsNameAndEmail(writer);
    }

    public NsUser getWriter() {
        return answerInfo.getWriter();
    }

    public String getContents() {
        return answerInfo.getContents();
    }

    @Override
    public String toString() {
        return answerInfo.toString();
    }
}
