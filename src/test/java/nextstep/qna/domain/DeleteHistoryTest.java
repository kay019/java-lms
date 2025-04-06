package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

public class DeleteHistoryTest {

  @Test
  void testEquals() {
    NsUser user = new NsUser(1L, "user", "password", "name", "email");
    DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.QUESTION, 1L, user);
    DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.QUESTION, 1L, user);

    assert deleteHistory1.equals(deleteHistory2);
  }
}
