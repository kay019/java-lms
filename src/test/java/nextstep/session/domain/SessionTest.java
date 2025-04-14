package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.session.domain.constraint.SessionConstraint;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.property.SessionProperty;
import nextstep.session.domain.property.SessionStatus;
import nextstep.session.domain.property.SessionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        Course course = new Course();
        SessionConstraint constraint = new SessionConstraint(200_000, 1);
        SessionDescriptor descriptor = new SessionDescriptor(new SessionImage(), new SessionPeriod(), new SessionProperty(SessionStatus.ENROLLING, SessionType.FREE));

        assertDoesNotThrow(() -> new Session(1L, course, constraint, descriptor));
    }
}
