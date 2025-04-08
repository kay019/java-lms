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

    public void delete() {
        for (Answer answer : values) {
            answer.setDeleted(true);
        }
    }

    public void checkOwner(NsUser loginUser) throws CannotDeleteException {
        for (Answer answer : values) {
            if (!answer.isOwner(loginUser)) {
                throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
            }
        }
    }

    public List<DeleteHistory> getDeleteHistories() {
        return values.stream()
                .map(Answer::ofDeleteHistory)
                .collect(Collectors.toList());
    }
}
