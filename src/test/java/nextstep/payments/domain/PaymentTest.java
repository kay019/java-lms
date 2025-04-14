package nextstep.payments.domain;

import nextstep.courses.domain.Course;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionDescriptor;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.constraint.SessionConstraint;
import nextstep.session.domain.property.SessionProperty;
import nextstep.session.domain.property.SessionStatus;
import nextstep.session.domain.property.SessionType;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PaymentTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        Course course = new Course();
        SessionConstraint constraint = new SessionConstraint(200_000, 1);
        SessionDescriptor descriptor = new SessionDescriptor(new SessionImage(), new SessionPeriod(), new SessionProperty(SessionStatus.ENROLLING, SessionType.FREE));

        session = new Session(1L, course, constraint, descriptor);
    }

    @DisplayName("Payment 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Payment(1L, session, NsUserTest.JAVAJIGI, 300_000L));
    }

    @DisplayName("두개의 Payment 강의와 유저 정보가 동일 판별")
    @Test
    public void testEqualsSessionUser() {
        Payment payment1 = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment(2L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment3 = new Payment(3L, session, NsUserTest.SANJIGI, 200_000L);

        assertAll(
            () -> assertThat(payment1.equalsSessionUser(payment2)).isTrue(),
            () -> assertThat(payment1.equalsSessionUser(payment3)).isFalse()
        );
    }
}
