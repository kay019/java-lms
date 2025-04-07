package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nextstep.users.domain.NsUser;

public class Answers {
    private final List<Answer> answers;

    public Answers(List<Answer> answers) {
        this.answers =
                new ArrayList<>(answers);
    }

    public void checkOwners(NsUser loginUser) {
        for (Answer answer : answers) {
            answer.checkOwner(loginUser);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(answers);
    }
}
