package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import nextstep.courses.domain.session.inner.FreeSessionType;
import nextstep.courses.domain.session.inner.PaidSessionType;
import nextstep.courses.domain.session.inner.SessionStatus;
import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Test;

public class SessionTest {
    public static final Session SESSION1 = new Session(1L, 1L, "free", new FreeSessionType(), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.OPEN, 0);
    public static final Session SESSION2 = new Session(2L, 1L, "paid", new PaidSessionType(10, 10000), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.OPEN, 0);
    public static final Session SESSION3 = new Session(3L, 1L, "free", new FreeSessionType(), LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 31), SessionStatus.CLOSED, 0);

    @Test
    void 수강신청상태(){
        boolean enroll1 = SESSION1.enroll(new Payment());
        assertThat(enroll1).isTrue();

        boolean enroll3 = SESSION3.enroll(new Payment());
        assertThat(enroll3).isFalse();
    }

}
