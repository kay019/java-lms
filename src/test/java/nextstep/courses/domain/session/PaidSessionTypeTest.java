package nextstep.courses.domain.session;

import static nextstep.courses.domain.session.SessionTest.SESSION2;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Test;

class PaidSessionTypeTest {

    @Test
    void 수강신청() {
        SessionType sessionType = new PaidSessionType(10, 10000);
        Payment payment = new Payment("1", 1L, 100L, 10000L);

        boolean enroll = sessionType.enroll(payment, SESSION2);

        assertThat(enroll).isTrue();
    }

    @Test
    void 수강료부족() {
        SessionType sessionType = new PaidSessionType(10, 10000);
        Payment payment = new Payment("1", 1L, 100L, 1000L);

        boolean enroll = sessionType.enroll(payment, SESSION2);

        assertThat(enroll).isFalse();
    }


    @Test
    void 인원초과() {
        Session session = new Session(2L, 1L, "paid", new PaidSessionType(10, 10000),
                LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.OPEN, 10);
        SessionType sessionType = new PaidSessionType(10, 10000);
        Payment payment = new Payment("1", 1L, 100L, 1000L);

        boolean enroll = sessionType.enroll(payment, session);

        assertThat(enroll).isFalse();
    }

}
