package nextstep.courses.domain.session.property;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionPropertyTest {

    @DisplayName("SessionProperty 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionProperty(SessionStatus.ENROLLING, SessionType.FREE));
    }
}
