package nextstep.qna.domain.qustion;

import nextstep.qna.domain.Question;
import nextstep.users.domain.NsUser;

public class QuestionDeletableWriter implements QuestionDeletable {

    @Override
    public boolean deletable(Question question, NsUser loginUser) {
        return question.isOwner(loginUser);
    }

    @Override
    public String reason() {
        return "질문을 삭제할 권한이 없습니다.";
    }
}
