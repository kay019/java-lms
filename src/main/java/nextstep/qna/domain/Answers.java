package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import nextstep.users.domain.NsUser;

public class Answers {
    private final List<Answer> answers;

    public Answers() {
        this(Collections.emptyList());
    }

    public Answers(List<Answer> answers) {
        this.answers =
                new ArrayList<>(answers);
    }

    public Answers addAnswer(Answer answer) {
        List<Answer> newAnswers = new ArrayList<>(answers);
        newAnswers.add(answer);
        return new Answers(newAnswers);
    }

    public void checkOwners(NsUser loginUser) {
        for (Answer answer : answers) {
            answer.checkOwner(loginUser);
        }
    }

    public void deleteAll() {
        for (Answer answer : answers) {
            answer.delete();
        }
    }

    public List<DeleteHistory> createHistories() {
        return this.answers.stream().map(Answer::createDeleteHistory).collect(Collectors.toList());
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
