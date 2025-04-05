package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
  public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
  public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

  @Test
  public void 삭제() {
    LocalDateTime createdDate = LocalDateTime.now();
    Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", createdDate);
    answer.delete();
    assertThat(answer)
        .isEqualTo(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", true, createdDate));
  }

  @Test
  public void 삭제_히스토리_변환() {
    assertThat(A1.toDeleteHistory())
        .isEqualTo(new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now()));
  }

}
