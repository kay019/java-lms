package nextstep.qna.domain.qustion;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.Question;
import nextstep.users.domain.NsUser;

public class QuestionDeletableWriter implements QuestionDeletable {

    static final String REASON = "질문을 삭제할 권한이 없습니다.";

    @Override
    public void checkDeletable(Question question, NsUser loginUser) throws CannotDeleteException {
        if (question.isOwner(loginUser)) {
            return;
        }
        throw new CannotDeleteException(REASON);
    }
}
