package nextstep.qna.domain;

import nextstep.qna.exception.CannotDeleteException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {

  private final NsUser user = new NsUser(1L, "userId", "password", "name", "email");
  private final NsUser otherUser = new NsUser(2L, "otherId", "password", "name", "email");
  private final Question question = new Question(1L, user, "title", "contents");
  private final Answer answer1 = new Answer(1L, user, question, "Answer1");
  private final Answer answer2 = new Answer(2L, user, question, "Answer2");

  @Test
  public void validateDeletable_모두_같은_작성자() throws CannotDeleteException {
    Answers answers = new Answers(List.of(answer1, answer2));

    answers.validateDeletable(user);
  }

  @Test
  public void validateDeletable_다른_작성자_포함() {
    Answers answers = new Answers(List.of(answer1, new Answer(3L, otherUser, question, "Other Answer")));

    assertThatThrownBy(
        () -> answers.validateDeletable(user))
        .isInstanceOf(CannotDeleteException.class)
        .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
  }

  @Test
  public void validateDeletable_빈_답변() throws CannotDeleteException {
    Answers answers = new Answers();

    answers.validateDeletable(user);
  }

  @Test
  public void delete_성공() {
    Answers answers = new Answers(List.of(answer1, answer2));

    List<DeleteHistory> deleteHistories = answers.delete();

    assertThat(deleteHistories).hasSize(2);
    assertThat(deleteHistories.get(0)).isEqualTo(new DeleteHistory(ContentType.ANSWER, 1L, user));
    assertThat(deleteHistories.get(1)).isEqualTo(new DeleteHistory(ContentType.ANSWER, 2L, user));
    assertThat(answer1.isDeleted()).isTrue();
    assertThat(answer2.isDeleted()).isTrue();
  }
}