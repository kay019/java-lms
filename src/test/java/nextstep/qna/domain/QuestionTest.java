package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class QuestionTest {

  public static final ContentInfo contentInfo1 = new ContentInfo(NsUserTest.JAVAJIGI, "contents1");
  public static final ContentInfo contentInfo2 = new ContentInfo(NsUserTest.SANJIGI, "contents2");

  public static final Question Q1 = new Question("title1", contentInfo1);
  public static final Question Q2 = new Question("title2", contentInfo2);

  public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");

  @Test
  void answers_유효성_검사_정상() throws CannotDeleteException {
    Q1.addAnswer(A1);
    Q1.checkAllAnswersByUser(NsUserTest.JAVAJIGI);
  }

  @Test
  void answers_유효성_검사_실패() {
    Q1.addAnswer(A1);
    assertThatThrownBy(() -> {
      Q1.checkAllAnswersByUser(NsUserTest.SANJIGI);
    }).isInstanceOf(CannotDeleteException.class);
  }

  @Test
  void question_delete_상태변경() {
    Q1.delete();
    assertTrue(Q1.isDeleted());
  }

  @Test
  void answers_delete_상태변경() {
    List<DeleteHistory> deleteHistories = new ArrayList<>();
    Q1.addAnswer(A1);
    Q1.deleteAnswersAndRecordHistory(deleteHistories);

    assertTrue(A1.isDeleted());
  }


}
