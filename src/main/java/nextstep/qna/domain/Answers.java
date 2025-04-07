package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Answers {
    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public void delete() {
        answers.forEach(Answer::delete);
    }

    public DeleteHistories deleteHistories() {
        return new DeleteHistories(answers.stream()
                .map(Answer::deleteHistory)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(answers);
    }
}
