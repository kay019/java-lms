package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    private final List<Answer> answers;

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        List<DeleteHistory> res = new ArrayList<>();
        for (Answer answer : answers) {
            res.add(answer.delete(loginUser));
        }
        return res;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }
}
