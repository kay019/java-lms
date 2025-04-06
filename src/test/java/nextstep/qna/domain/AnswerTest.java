package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AnswerTest {
  public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
  public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

  @DisplayName("생성자")
  @Test
  public void testConstructor() {
    assertDoesNotThrow(() -> new Answer(
        0L,
        NsUserTest.JAVAJIGI,
        QuestionTest.Q1,
        "Answers Contents1",
        false
        )
    );
  }

  @DisplayName("삭제 히스토리 변환")
  @Test
  public void testToDeleteHistory() {
    Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    assertThat(answer.toDeleteHistory())
        .isEqualTo(new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now()));
  }

  @DisplayName("삭제")
  @Test
  public void testDelete() {
    Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    answer.delete();
    assertThat(answer)
        .isEqualTo(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", true));
  }

  @DisplayName("삭제된 데이터 여부 확인")
  @Test
  public void testIsDeleted() {
    Answer deleted = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", true);
    Answer notDeleted = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", false);
    assertAll(
        () -> assertThat(deleted.isDeleted())
            .isEqualTo(true),
        () -> assertThat(notDeleted.isDeleted())
            .isEqualTo(false)
    );
  }
}
