package nextstep.qna.domain.qustion;

import nextstep.qna.domain.Question;
import nextstep.users.domain.NsUser;

public class QuestionDeletableAnswer implements QuestionDeletable {

    @Override
    public boolean deletable(Question question, NsUser loginUser) {
        if (question.answerEmpty()) {
            return true;
        }
        return question.allAnswerFromWriter();
    }

    @Override
    public String reason() {
        return "다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.";
    }
}
