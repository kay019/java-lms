package nextstep.session.domain.payment;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.image.SessionImage;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionPaymentsTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        session = new Session(1L, new Course(), new SessionImage(), new SessionPayments(), new SessionPeriod());
    }

    @DisplayName("SessionPayments 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionPayments(new SessionFee(800_000), new SessionCapacity(80)));
    }

    @DisplayName("최대 수강 인원이 찼는지 확인")
    @Test
    public void testIsFull() {
        Payments payments = new Payments(List.of(new Payment()));
        SessionPayments sessionPayments = new SessionPayments(800_000, 1, payments);

        assertThat(sessionPayments.isFull()).isTrue();
    }

    @DisplayName("수강료와 지불 요금 금액이 맞는지 확인")
    @Test
    public void testMatchesFee() {
        SessionPayments sessionPayments = new SessionPayments(200_000, 1);
        Payment payment1 = new Payment(1L, session, NsUserTest.JAVAJIGI, 200_000L);
        Payment payment2 = new Payment(1L, session, NsUserTest.JAVAJIGI, 300_000L);

        assertAll(
            () -> assertThat(sessionPayments.matchesFee(payment1)).isTrue(),
            () -> assertThat(sessionPayments.matchesFee(payment2)).isFalse()
        );
    }
}
