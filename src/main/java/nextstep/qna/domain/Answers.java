package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Answers {

  private final List<Answer> answerList;

  public Answers() {
    this.answerList = Collections.emptyList();
  }

  public Answers(List<Answer> answers) {
    this.answerList = Collections.unmodifiableList(new ArrayList<>(answers));
  }

  public Answers(Answers existingAnswers, Answer newAnswer) {
    this(createNewList(existingAnswers.answerList, newAnswer));
  }

  private static List<Answer> createNewList(List<Answer> existingAnswers, Answer newAnswer) {
    List<Answer> newList = new ArrayList<>(existingAnswers);
    newList.add(newAnswer);
    return newList;
  }

  public boolean isEmpty() {
    return answerList.isEmpty();
  }

  public boolean areAllAnswersSameWriter(NsUser loginUser) {
    return answerList.stream()
        .allMatch(answer -> answer.isOwner(loginUser));
  }

  public List<DeleteHistory> delete() {
    List<DeleteHistory> deleteHistoryList = new ArrayList<>();

    for (Answer answer : answerList) {
      DeleteHistory deleteHistory = answer.delete();
      deleteHistoryList.add(deleteHistory);
    }

    return deleteHistoryList;
  }
}
