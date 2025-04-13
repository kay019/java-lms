package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Answers {
    private List<Answer> answers = new ArrayList<>();

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return answers.stream().allMatch(answer -> answer.isOwner(loginUser));
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        validDelete(loginUser);
        List<DeleteHistory> deleteHistoriesList = new ArrayList<>();
        for (Answer answer : answers) {
            deleteHistoriesList.add(answer.delete(loginUser));
        }
        return deleteHistoriesList;
    }

    private void validDelete(NsUser loginUser) throws CannotDeleteException {
        if(!isOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }
}
