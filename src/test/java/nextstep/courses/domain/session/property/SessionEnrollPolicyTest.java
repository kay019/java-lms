package nextstep.courses.domain.session.property;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionEnrollPolicyTest {

    @DisplayName("SessionProperty 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionEnrollPolicy(SessionStatus.ENROLLING, SessionType.FREE));
    }
}
