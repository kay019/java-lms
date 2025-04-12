package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionsTest {

    @DisplayName("Sessions 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(Sessions::new);
    }
}
