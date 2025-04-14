package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnswerTest {

  private final NsUser user = new NsUser(1L, "userId", "password", "name", "email");
  private final Question question = new Question(1L, user, "title", "contents");
  private final Long answerId = 1L;
  private final Answer answer = new Answer(answerId, user, question, "answer contents");

  @Test
  void delete_성공() {
    DeleteHistory deleteHistory = answer.delete();

    assertTrue(answer.isDeleted());
    assertThat(deleteHistory).isEqualTo(
        new DeleteHistory(ContentType.ANSWER, answerId, user));
  }
}

