package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Answers {
    private final List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void validateOwnership(NsUser loginUser) throws CannotDeleteException {
        for (Answer answer : answers) {
            if (!answer.isOwner(loginUser)) {
                throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
            }
        }
    }

    public boolean areAllOwnedBy(NsUser loginUser) {
        return answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }

    public List<DeleteHistory> deleteAll() {
        return answers.stream().map(Answer::delete).collect(Collectors.toList());
    }
}
