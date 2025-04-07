package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {
    private List<Answer> answers = new ArrayList<>();

    public List<DeleteHistory> toAnswersDeleteHistories() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        for (Answer answer : answers) {
            deleteHistories.add(answer.toAnswerDeleteHistory());
        }
        return deleteHistories;
    }

    public void deleteAnswers() {
        for (Answer answer : answers) {
            answer.delete();
        }
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public void validateAnswerByDifferentPerson(NsUser user) throws CannotDeleteException {
        for (Answer answer : answers) {
            if (!answer.isOwner(user)) {
                throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
            }
        }
    }
}
