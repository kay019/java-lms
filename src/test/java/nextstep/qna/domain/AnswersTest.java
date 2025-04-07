package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswersTest {

  private NsUser user;
  private NsUser otherUser;
  private Question question;
  private Answer answer1;
  private Answer answer2;

  @BeforeEach
  public void setUp() {
    user = new NsUser(1L, "userId", "password", "name", "email");
    otherUser = new NsUser(2L, "otherId", "password", "name", "email");
    question = new Question(1L, user, "title", "contents");
    answer1 = new Answer(1L, user, question, "Answer1");
    answer2 = new Answer(2L, user, question, "Answer2");
  }

  @Test
  public void isEmpty_빈_답변여부_테스트() {
    Answers answers = new Answers();
    assertThat(answers.isEmpty()).isTrue();
  }

  @Test
  public void areAllAnswersSameWriter_모두_같은_작성자() {
    Answers answers = new Answers(List.of(answer1, answer2));

    assertThat(answers.areAllAnswersSameWriter(user)).isTrue();
  }

  @Test
  public void areAllAnswersSameWriter_다른_작성자_포함() {
    Answers answers = new Answers(List.of(answer1, new Answer(3L, otherUser, question, "Other Answer")));
    assertThat(answers.areAllAnswersSameWriter(user)).isFalse();
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