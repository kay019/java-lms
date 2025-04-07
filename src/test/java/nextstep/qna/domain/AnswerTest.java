package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnswerTest {

  private NsUser user;
  private long answerId;
  private Answer answer;

  @BeforeEach
  void setUp() {
    user = new NsUser(1L, "userId", "password", "name", "email");
    Question question = new Question(1L, user, "title", "contents");
    answerId = 1L;
    answer = new Answer(answerId, user, question, "answer contents");
  }

  @Test
  void delete_성공() {
    DeleteHistory deleteHistory = answer.delete();

    assertTrue(answer.isDeleted());
    assertThat(deleteHistory).isEqualTo(
        new DeleteHistory(ContentType.ANSWER, answerId, user));
  }
}

