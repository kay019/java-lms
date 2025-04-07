package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AnswerTest {
    public static final Answer A1 = new Answer(1L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(1L, NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("Answer 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Answer(1L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1"));
    }

    @DisplayName("삭제 후 삭제 히스토리를 가져옴")
    @Test
    public void testDelete() {
        Answer answer = new Answer(1L, NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        DeleteHistory deleteHistory = answer.delete();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistory)
            .isEqualTo(new DeleteHistory(ContentType.ANSWER, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()));
    }
}
