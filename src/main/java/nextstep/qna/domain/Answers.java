package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nextstep.users.domain.NsUser;

public class Answers {
    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public Answers withAddedAnswer(Answer answer) {
        List<Answer> newAnswers = new ArrayList<>(answers);
        newAnswers.add(answer);
        return new Answers(newAnswers);
    }

    public List<DeleteHistory> deleteAll(NsUser loginUser) {
        return answers.stream()
            .map(answer -> answer.delete(loginUser))
            .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(answers);
    }
}
