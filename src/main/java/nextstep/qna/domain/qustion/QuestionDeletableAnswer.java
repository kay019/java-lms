package nextstep.qna.domain.qustion;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.Question;
import nextstep.users.domain.NsUser;

public class QuestionDeletableAnswer implements QuestionDeletable {

    static final String REASON = "다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.";

    @Override
    public void checkDeletable(Question question, NsUser loginUser) throws CannotDeleteException {
        if (question.answerEmpty() || question.allAnswerFromWriter()) {
            return;
        }
        throw new CannotDeleteException(REASON);
    }
}
