package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        SessionConstraint constraint = new SessionConstraint();
        SessionDescriptor descriptor = new SessionDescriptor();

        assertDoesNotThrow(() -> new Session(1L, constraint, descriptor));
    }
}
