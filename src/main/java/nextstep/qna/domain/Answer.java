package nextstep.qna.domain;

import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Answer {
  private Long id;

  private NsUser writer;

  private Question question;

  private String contents;

  private boolean deleted = false;

  private LocalDateTime createdDate = LocalDateTime.now();

  private LocalDateTime updatedDate;

  public Answer() {
  }

  public Answer(NsUser writer, Question question, String contents) {
    this(null, writer, question, contents, false, LocalDateTime.now());
  }

  public Answer(NsUser writer, Question question, String contents, LocalDateTime createdDate) {
    this(null, writer, question, contents, false, createdDate);
  }


  public Answer(NsUser writer, Question question, String contents, boolean deleted) {
    this(null, writer, question, contents, deleted, LocalDateTime.now());
  }

  public Answer(NsUser writer, Question question, String contents, boolean deleted, LocalDateTime createdDate) {
    this(null, writer, question, contents, deleted, createdDate);
  }

  public Answer(Long id, NsUser writer, Question question, String contents) {
    this(id, writer, question, contents, false, LocalDateTime.now());
  }

  public Answer(Long id, NsUser writer, Question question, String contents, boolean deleted, LocalDateTime createdDate) {
    this.id = id;
    if (writer == null) {
      throw new UnAuthorizedException();
    }

    if (question == null) {
      throw new NotFoundException();
    }

    this.writer = writer;
    this.question = question;
    this.contents = contents;
    this.deleted = deleted;
    this.createdDate = createdDate;
  }

  public DeleteHistory toDeleteHistory() {
    return new DeleteHistory(ContentType.ANSWER, this.id, this.writer, LocalDateTime.now());
  }

  public void delete() {
    this.deleted = true;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public boolean isOwner(NsUser writer) {
    return this.writer.equals(writer);
  }

  public NsUser getWriter() {
    return writer;
  }

  public void toQuestion(Question question) {
    this.question = question;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Answer answer = (Answer) o;
    return deleted == answer.deleted &&
        Objects.equals(id, answer.id) &&
        Objects.equals(writer, answer.writer) &&
        Objects.equals(question, answer.question) &&
        Objects.equals(contents, answer.contents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, writer, question, contents, deleted, createdDate, updatedDate);
  }

  @Override
  public String toString() {
    return "Answer [id=" + id + ", writer=" + writer + ", contents=" + contents + "]";
  }
}
