package nextstep.session.domain.property;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionDescriptor;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.constraint.SessionConstraint;
import nextstep.session.domain.image.SessionImage;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionTypeTest {

    @DisplayName("무료강의는 수당신청을 조건없이 할 수 있다.")
    @Test
    public void testCanEnroll_Free() {
        Course course = new Course();
        SessionConstraint constraint = new SessionConstraint(200_000, 1);
        SessionDescriptor descriptor = new SessionDescriptor(new SessionImage(), new SessionPeriod(), new SessionProperty(SessionStatus.ENROLLING, SessionType.FREE));

        Session session = new Session(1L, course, constraint, descriptor);
        Payments payments = new Payments(List.of(new Payment(1L, session, NsUserTest.JAVAJIGI, 300_000L)));
        Payment payment = new Payment(1L, session, NsUserTest.SANJIGI, 300_000L);

        assertThat(SessionType.FREE.canEnroll(constraint, payments, payment)).isTrue();
    }

    @DisplayName("유료강의는 인원여유가 있어야 수강신청을 할 수 있다.")
    @Test
    public void testCanEnroll_PaidWithSameAmount() {
        Course course = new Course();
        SessionConstraint constraint = new SessionConstraint(200_000, 1);
        SessionDescriptor descriptor = new SessionDescriptor(new SessionImage(), new SessionPeriod(), new SessionProperty(SessionStatus.ENROLLING, SessionType.FREE));

        Session session = new Session(1L, course, constraint, descriptor);
        Payments payments = new Payments(List.of(new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L)));
        Payment payment = new Payment(1L, session, NsUserTest.SANJIGI, 200_000L);

        assertThat(SessionType.PAID.canEnroll(constraint, payments, payment)).isFalse();
    }

    @DisplayName("유료강의는 요금조건이 맞아야 수강신청을 할 수 있다.")
    @Test
    public void testCanEnroll_PaidWit() {
        Course course = new Course();
        SessionConstraint constraint = new SessionConstraint(200_000, 1);
        SessionDescriptor descriptor = new SessionDescriptor(new SessionImage(), new SessionPeriod(), new SessionProperty(SessionStatus.ENROLLING, SessionType.FREE));

        Session session = new Session(1L, course, constraint, descriptor);
        Payments payments = new Payments(List.of(new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L)));
        Payment payment = new Payment(1L, session, NsUserTest.SANJIGI, 300_000L);

        assertThat(SessionType.PAID.canEnroll(constraint, payments, payment)).isFalse();
    }
}
