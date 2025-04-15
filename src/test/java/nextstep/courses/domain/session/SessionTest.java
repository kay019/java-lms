package nextstep.courses.domain.session;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.property.SessionProperty;
import nextstep.courses.domain.session.property.SessionStatus;
import nextstep.courses.domain.session.property.SessionType;
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
