package nextstep.qna.domain.qustion;

import nextstep.qna.domain.Question;
import nextstep.users.domain.NsUser;

public interface QuestionDeletable {
    boolean deletable(Question question, NsUser loginUser);

    String reason();
}
