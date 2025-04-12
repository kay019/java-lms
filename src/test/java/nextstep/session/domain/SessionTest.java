package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.payment.SessionCapacity;
import nextstep.session.domain.payment.SessionFee;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.property.SessionStatus;
import nextstep.session.domain.property.SessionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {

    private Course course;
    private SessionPayments payments;
    private SessionPeriod period;

    @BeforeEach
    void setUp() {
        course = new Course("test-course-1", 1L);
        payments = new SessionPayments(new SessionFee(500_000), new SessionCapacity(80));

        period = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now());
    }

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Session(1L, course, new SessionImage(), payments, period, SessionStatus.PREPARING, SessionType.FREE));
    }
}
