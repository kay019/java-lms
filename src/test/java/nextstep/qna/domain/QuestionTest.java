package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @DisplayName("Question 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Question(1L, NsUserTest.JAVAJIGI, "title1", "contents1"));
    }

    @DisplayName("유저가 삭제할수 없는 질문이면 예외를 던짐 - 질문 작성자가 아닌 경우")
    @Test
    public void testCheckDeletableByUser_throwExceptionByQuestionOwner() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        assertThatThrownBy(() -> question.checkDeletableByUser(NsUserTest.SANJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining("질문을 삭제할 권한이 없습니다.");
    }

    @DisplayName("유저가 삭제할수 없는 질문이면 예외를 던짐 - 질문 작성자가 아닌 경우")
    @Test
    public void testCheckDeletableByUser_throwExceptionByAnswerOwner() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        AnswerTest.A2.link(question);
        assertThatThrownBy(() -> question.checkDeletableByUser(NsUserTest.JAVAJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @DisplayName("유저가 삭제할수 있는 질문이면 예외를 던지지 않음")
    @Test
    public void testCheckDeletableByUser_doesNotThrowException() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        assertDoesNotThrow(() -> question.checkDeletableByUser(NsUserTest.JAVAJIGI));
    }

    @DisplayName("삭제 히스토리 변환")
    @Test
    public void testToDeleteHistory() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        AnswerTest.A1.link(question);

        assertThat(question.toDeleteHistory())
            .containsExactly(
                new DeleteHistory(ContentType.QUESTION, 0L, NsUserTest.JAVAJIGI, LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now())
            );
    }
}
