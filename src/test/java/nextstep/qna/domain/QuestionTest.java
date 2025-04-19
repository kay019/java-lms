package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class QuestionTest {
    public static final Question Q1 = new Question("1", NsUserTest.JAVAJIGI, "title1", "contents1");

    @DisplayName("Question 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Question("1", NsUserTest.JAVAJIGI, "title1", "contents1"));
    }

    @DisplayName("삭제 후 삭제 히스토리 목록들을 가져옴")
    @Test
    public void testDelete() throws CannotDeleteException {
        Question question = new Question("1", NsUserTest.JAVAJIGI, "title1", "contents1");
        DeleteHistory deleteHistory = question.delete(NsUserTest.JAVAJIGI);

        assertAll(
            () -> assertThat(question.isDeleted()).isTrue(),
            () -> assertThat(deleteHistory)
                .isEqualTo(new DeleteHistory(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()))
        );
    }

    @DisplayName("질문 작성자가 아닌 경우 삭제 시 예외를 던짐")
    @Test
    public void testDelete_throwException() {
        Question question = new Question("1", NsUserTest.JAVAJIGI, "title1", "contents1");

        assertThatThrownBy(() -> question.delete(NsUserTest.SANJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining("질문을 삭제할 권한이 없습니다.");
    }
}
