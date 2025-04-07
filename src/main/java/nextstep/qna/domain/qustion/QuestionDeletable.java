package nextstep.qna.domain.qustion;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.Question;
import nextstep.users.domain.NsUser;

public interface QuestionDeletable {
    void checkDeletable(Question question, NsUser loginUser)  throws CannotDeleteException;
}
