package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AnswersTest {

    @DisplayName("Answers 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Answers(List.of(new Answer("1", NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1"))));
    }

    @DisplayName("삭제 후 삭제 히스토리 목록들을 가져옴")
    @Test
    public void testDelete() throws CannotDeleteException {
        Answers answers = new Answers();
        answers.add(AnswerTest.A1);
        List<DeleteHistory> deleteHistoryList = answers.delete(NsUserTest.JAVAJIGI);

        assertAll(
            () -> assertThat(deleteHistoryList)
                .containsExactly(
                    new DeleteHistory(ContentType.ANSWER, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now())
                )
        );
    }

    @DisplayName("질문 작성자가 아닌 경우 삭제 시 예외를 던짐")
    @Test
    public void testDelete_throwException() {
        Answers answers = new Answers();
        answers.add(AnswerTest.A1);
        answers.add(AnswerTest.A2);

        assertThatThrownBy(() -> answers.delete(NsUserTest.SANJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining("답변을 삭제할 권한이 없습니다.");
    }
}
