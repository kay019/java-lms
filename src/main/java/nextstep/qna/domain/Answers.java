package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {
    private final List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean hasAnswerFromOtherUser(NsUser user) {
        for (Answer answer : answers) {
            if (!answer.isOwner(user)) {
                return true;
            }
        }
        return false;
    }

    public List<DeleteHistory> deleteAllAnswers(NsUser loginUser) throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : answers) {
            answer.delete(loginUser);
            deleteHistories.add(DeleteHistory.ofAnswer(answer.getId(), answer.getWriter()));
        }
        return deleteHistories;
    }
}

