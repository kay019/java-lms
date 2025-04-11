package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Answers {
    private List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean isOwnerOfAll(NsUser loginUser) {
        return answers.stream()
                .allMatch(answer -> answer.isOwner(loginUser));
    }

    public List<DeleteHistory> deleteAll() {
        return answers.stream()
                .map(Answer::delete)
                .collect(Collectors.toList());
    }
}
