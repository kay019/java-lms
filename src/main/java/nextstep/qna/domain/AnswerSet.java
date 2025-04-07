package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class AnswerSet {
    List<Answer> answerList = new ArrayList<>();

    public AnswerSet(Answer... answers) {
        answerList.addAll(Arrays.asList(answers));
    }

    public void add(Answer answer) {
        answerList.add(answer);
    }

    public void validateForDelete(NsUser loginUser) throws CannotDeleteException {
        if(answerList.isEmpty()) {
            return;
        }

        for (Answer answer : this.answerList) {
            answer.validateForDelete(loginUser);
        }
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        this.validateForDelete(loginUser);

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : this.answerList) {
            deleteHistories.add(answer.delete(loginUser));
        }

        return deleteHistories;
    }
}
