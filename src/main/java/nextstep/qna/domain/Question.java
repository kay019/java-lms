package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.List;

public class Question {

  private Long id;

  private String title;

  private ContentInfo contentInfo;

  private Answers answers;

  private boolean deleted = false;

  private DateInfo dateInfo;

  public Question() {
  }

  public Question(String title, ContentInfo contentInfo) {
    this(0L, title, contentInfo);
  }

  public Question(Long id, NsUser writer, String title, String content) {
    this(id, title, new ContentInfo(writer, content));
  }

  public Question(Long id, String title, ContentInfo contentInfo) {
    this.id = id;
    this.title = title;
    this.contentInfo = contentInfo;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void addAnswer(Answer answer) {
    answer.toQuestion(this);
    answers.add(answer);
  }

  public boolean isOwner(NsUser loginUser) {
    return contentInfo.isOwner(loginUser);
  }

  public void delete() {
    this.deleted = true;
  }

  public boolean isDeleted() {
    return deleted;
  }

  @Override
  public String toString() {
    return "Question [id=" + getId() + ", title=" + title + ", contentInfo=" + contentInfo + "]";
  }

  public void checkAllAnswersByUser(NsUser loginUser) throws CannotDeleteException {
    answers.checkAllAnswersByUser(loginUser);
  }

  public void deleteAnswersAndRecordHistory(List<DeleteHistory> deleteHistories) {
    answers.deleteAnswersAndRecordHistory(deleteHistories);
  }

  public void addDeleteHistory(List<DeleteHistory> deleteHistories) {
    contentInfo.addDeleteHistory(ContentType.QUESTION, deleteHistories, id);
  }
}
