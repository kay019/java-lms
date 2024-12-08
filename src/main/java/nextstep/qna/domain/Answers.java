package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {
    private final List<Answer> values;

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> answers) {
        this.values = answers;
    }

    public DeleteHistories delete(NsUser loginUser) throws CannotDeleteException {
        DeleteHistories deleteHistories = new DeleteHistories();

        for (Answer answer : this.values) {
            deleteHistories.add(answer.delete(loginUser));
        }

        return deleteHistories;
    }

    public void add(Answer answer) {
        this.values.add(answer);
    }
}
