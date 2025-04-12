package nextstep.payments.domain;

import nextstep.courses.domain.Course;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaymentsTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        Course course = new Course("test-course-1", 1L);
        SessionPayments payments = new SessionPayments(500_000, 80);
        SessionPeriod period = new SessionPeriod(LocalDateTime.now(), LocalDateTime.now());
        session = new Session(1L, course, new SessionImage(), payments, period);
    }

    @DisplayName("Payments 인스턴스 생성 테스트")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Payments());
    }

    @DisplayName("payment 추가")
    @Test
    public void testAdd() {
        Payment payment = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payments payments = new Payments();
        assertDoesNotThrow(() -> payments.add(payment));
    }

    @DisplayName("payment 추가 - 동일 유저가 한 코드에 대해서 결재를 여러번 하면 예외 던짐")
    @Test
    public void testAdd_throwException() {
        Payment payment1 = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment(2L, session, NsUserTest.JAVAJIGI, 200_000L);

        Payments payments = new Payments();
        assertAll(
            () -> assertDoesNotThrow(() -> payments.add(payment1)),
            () -> assertThatThrownBy(() -> payments.add(payment2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("한 유저가 동일한 코스에 두번 결재할 수 없습니다.")
        );
    }
}
