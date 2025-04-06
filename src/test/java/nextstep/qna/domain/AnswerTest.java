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
    assertDoesNotThrow(() ->
        new Answer(
            0L,
            NsUserTest.JAVAJIGI,
            QuestionTest.Q1,
            "Answers Contents1", false,
            LocalDateTime.now()
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
    LocalDateTime createdDate = LocalDateTime.now();
    Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", createdDate);
    answer.delete();
    assertThat(answer)
        .isEqualTo(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", true, createdDate));
  }

  @DisplayName("삭제된 데이터 여부 확인")
  @Test
  public void testIsDeleted() {
    assertAll(
        () -> assertThat(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", true).isDeleted())
            .isEqualTo(true),
        () -> assertThat(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1", false).isDeleted())
            .isEqualTo(false)
    );
  }

  @DisplayName("답변 질문 등록")
  @Test
  public void testToQuestion() {
    Answer answer = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    answer.toQuestion(QuestionTest.Q2);
    assertThat(answer).isEqualTo(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q2, "Answers Contents1"));
  }
}
