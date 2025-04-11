package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Answers {

    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        if (isNotOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
        return answers.stream().map(Answer::delete).collect(Collectors.toList());
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean isNotOwner(NsUser loginUser) {
        return !answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }
}
