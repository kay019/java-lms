package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

  public static final Question Q1 = new Question(
      NsUserTest.JAVAJIGI,
      "title1",
      "contents1",
      List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
  );
  public static final Question Q2 = new Question(
      NsUserTest.SANJIGI,
      "title2",
      "contents2",
      List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
  );

  @DisplayName("생성자")
  @Test
  public void testConstructor() {
    assertAll(
        () -> assertDoesNotThrow(() -> new Question(
            1L,
            NsUserTest.JAVAJIGI,
            "title1",
            "contents1",
            List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1")),
            false,
            LocalDateTime.now(),
            LocalDateTime.now()
        )),
        () -> assertDoesNotThrow(() -> new Question(
            1L,
            NsUserTest.JAVAJIGI,
            "title1",
            "contents1",
            List.of(new Answer(NsUserTest.SANJIGI, new Question(), "test content1")),
            false,
            LocalDateTime.now(),
            LocalDateTime.now()
        ))
    );
  }

  @DisplayName("답변 추가")
  @Test
  public void testAddAnswer() {
    Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1", List.of(AnswerTest.A1));
    assertThat(question)
        .isEqualTo(new Question(NsUserTest.JAVAJIGI, "title1", "contents1", List.of(AnswerTest.A1)));
  }

  @DisplayName("질문 모든 답변 특정 유저 답변인지 확인")
  @Test
  public void testIsAllAnswersWrittenByUser() {
    Question javajigiQuestion = new Question(
        NsUserTest.JAVAJIGI,
        "title1",
        "contents1",
        List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
    );
    Question sanjigiQuestion = new Question(
        NsUserTest.SANJIGI,
        "title2",
        "contents2",
        List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
    );
    assertAll(
        () -> assertThat(javajigiQuestion.isAllAnswersWrittenByUser(NsUserTest.JAVAJIGI)).isEqualTo(true),
        () -> assertThat(sanjigiQuestion.isAllAnswersWrittenByUser(NsUserTest.SANJIGI)).isEqualTo(false)
    );
  }

  @DisplayName("삭제된 질문인지 확인")
  @Test
  public void testIsDeleted() {
    assertAll(
        () -> assertThat(new Question(NsUserTest.JAVAJIGI, "title1", "contents1", true).isDeleted())
            .isEqualTo(true),
        () -> assertThat(new Question(NsUserTest.JAVAJIGI, "title1", "contents1", false).isDeleted())
            .isEqualTo(false)
    );
  }

  @DisplayName("유저가 삭제 할 수 있는 질의 확인")
  @Test
  public void testCheckDeletableByUser() {
    Question javagijiQuestion = new Question(
        NsUserTest.JAVAJIGI,
        "title1",
        "contents1",
        List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
    );
    Question sanjigiQuestion = new Question(
        NsUserTest.SANJIGI,
        "title2",
        "contents2",
        List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
    );
    assertAll(
        () -> assertDoesNotThrow(() ->  javagijiQuestion.checkDeletableByUser(NsUserTest.JAVAJIGI)),
        () -> assertThrows(CannotDeleteException.class, () -> javagijiQuestion.checkDeletableByUser(NsUserTest.SANJIGI)),
        () -> assertThrows(CannotDeleteException.class, () -> sanjigiQuestion.checkDeletableByUser(NsUserTest.JAVAJIGI)),
        () -> assertThrows(CannotDeleteException.class, () -> sanjigiQuestion.checkDeletableByUser(NsUserTest.SANJIGI))
    );
  }

  @DisplayName("질문 및 모든 답변 제거")
  @Test
  public void testDeleteWithAllAnswers() {
    Question question = new Question(
        NsUserTest.JAVAJIGI,
        "title1",
        "contents1",
        List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
    );
    question.deleteWithAllAnswers();
    assertThat(question)
        .isEqualTo(
            new Question(
                NsUserTest.JAVAJIGI,
                "title1",
                "contents1",
                List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1", true)),
                true
            )
        );
  }

  @DisplayName("삭제 히스토리 변환")
  @Test
  public void testToDeleteHistory() {
    Question javagijiQuestion = new Question(
        NsUserTest.JAVAJIGI,
        "title1",
        "contents1",
        List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1"))
    );
    assertThat(javagijiQuestion.toDeleteHistory())
        .contains(
            new DeleteHistory(ContentType.QUESTION, 0L,  NsUserTest.JAVAJIGI, LocalDateTime.now()),
            new DeleteHistory(ContentType.ANSWER, null,NsUserTest.JAVAJIGI, LocalDateTime.now())
        );
  }
}
