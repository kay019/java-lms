package nextstep.qna.domain;

import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnswerTest {

  private NsUser user;
  private NsUser otherUser;
  private long questionId;
  private Question question;
  private long answerId;
  private Answer answer;

  @BeforeEach
  void setUp() {
    user = new NsUser(1L, "userId", "password", "name", "email");
    otherUser = new NsUser(2L, "otherId", "password", "name", "email");
    questionId = 1L;
    question = new Question(questionId, user, "title", "contents");
    answerId = 1L;
    answer = new Answer(answerId, user, question, "answer contents");
  }

  @Test
  void delete_성공() {
    DeleteHistory deleteHistory = answer.delete(user);

    assertTrue(answer.isDeleted());
    assertThat(deleteHistory).isEqualTo(
        new DeleteHistory(ContentType.ANSWER, answerId, user));
  }

  @Test
  void delete_다른_사용자가_삭제_시도() {
    assertThatThrownBy(() -> answer.delete(otherUser))
        .isInstanceOf(UnAuthorizedException.class)
        .hasMessage("답변을 삭제할 권한이 없습니다.");
  }
}

