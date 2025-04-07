package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nextstep.users.domain.NsUser;

public class Answers {
    private final List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);
    }

    public List<DeleteHistory> delete(NsUser loginUser) {
        return answers
            .stream()
            .map(answer -> answer.delete(loginUser))
            .collect(Collectors.toList());
    }

}
