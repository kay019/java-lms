package nextstep.qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AnswersTest {

    @DisplayName("Answers 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(Answers::new);
    }
}
