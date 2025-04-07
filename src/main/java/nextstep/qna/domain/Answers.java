package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Answers {

    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public List<DeleteHistory> toDeleteHistory() {
        return answers.stream().map(Answer::toDeleteHistory).collect(Collectors.toList());
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public void delete() {
        answers.forEach(Answer::delete);
    }

    public boolean isNotOwner(NsUser loginUser) {
        return !answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }
}
