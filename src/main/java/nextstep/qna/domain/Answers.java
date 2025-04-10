package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Answers {
    private final List<Answer> values;

    public Answers(List<Answer> answers) {
        this.values = answers;
    }

    public List<Answer> getValues() {
        return values;
    }

    public Answers add(Answer answer) {
        List<Answer> newList = new ArrayList<>(this.values);
        newList.add(answer);
        return new Answers(newList);
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        checkOwner(loginUser);
        return values.stream()
                .map(Answer::delete)
                .collect(Collectors.toList());
    }

    public void checkOwner(NsUser loginUser) throws CannotDeleteException {
        for (Answer answer : values) {
            answer.checkOwner(loginUser);
        }
    }
}
