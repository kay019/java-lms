package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
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

  @Test
  public void 생성자_테스트() {
    assertAll(
        () -> assertDoesNotThrow(() -> new Question(
            1L,
            NsUserTest.JAVAJIGI,
            "title1",
            "contents1",
            List.of(new Answer(NsUserTest.JAVAJIGI, new Question(), "test content1")),
            false
        )),
        () -> assertDoesNotThrow(() -> new Question(
            1L,
            NsUserTest.JAVAJIGI,
            "title1",
            "contents1",
            List.of(new Answer(NsUserTest.SANJIGI, new Question(), "test content1")),
            false
        ))
    );
  }

  @Test
  public void 삭제_가능한_질문인지_확인() {
    assertAll(
        () -> assertDoesNotThrow(() -> Q1.checkDeletableByUser(NsUserTest.JAVAJIGI)),
        () -> assertThrows(CannotDeleteException.class, () -> Q1.checkDeletableByUser(NsUserTest.SANJIGI)),
        () -> assertThrows(CannotDeleteException.class, () -> Q2.checkDeletableByUser(NsUserTest.JAVAJIGI)),
        () -> assertThrows(CannotDeleteException.class, () -> Q2.checkDeletableByUser(NsUserTest.SANJIGI))
    );
  }

  @Test
  public void 질문의_답변이_특정유저의_것인지_확인() {
    assertAll(
        () -> assertThat(Q1.isAllAnswersWrittenByUser(NsUserTest.JAVAJIGI)).isEqualTo(true),
        () -> assertThat(Q1.isAllAnswersWrittenByUser(NsUserTest.SANJIGI)).isEqualTo(false)
    );
  }

  @Test
  public void 질문_및_모든답변_제거() {
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

  @Test
  public void 삭제_히스토리_변환() {
    assertThat(Q1.toDeleteHistory())
        .contains(
            new DeleteHistory(ContentType.QUESTION, 0L,  NsUserTest.JAVAJIGI, LocalDateTime.now()),
            new DeleteHistory(ContentType.ANSWER, null,NsUserTest.JAVAJIGI, LocalDateTime.now())
        );
  }
}
