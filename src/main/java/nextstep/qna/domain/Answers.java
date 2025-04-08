package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public List<DeleteHistory> markAsDeletedBy(NsUser loginUser) throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : answers) {
            deleteHistories.add(answer.markAsDeletedBy(loginUser));
        }
        return deleteHistories;
    }
}
