package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Answers {

  private List<Answer> answers = new ArrayList<>();


  public void add(Answer answer) {
    answers.add(answer);
  }

  public void checkAllAnswersByUser(NsUser loginUser) throws CannotDeleteException {
    for (Answer answer : answers) {
      if (!answer.isOwner(loginUser)) {
        throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
      }
    }
  }

  public void deleteAnswersAndRecordHistory(List<DeleteHistory> deleteHistories) {
    for (Answer answer : answers) {
      answer.delete();
      answer.addDeleteHistory(deleteHistories);
    }
  }
}
