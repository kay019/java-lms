package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.support.builder.FreeSessionBuilder;
import nextstep.support.builder.PaymentBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FreeSessionTest {

    @Test
    @DisplayName("수강 신청을 하면 수강 대기 상태가 된다.")
    void requestEnrollTest() {
        int approvedStudent = 10;
        Payment payment = new PaymentBuilder()
                .amount(0L)
                .sessionId(1L)
                .nsUserId(1L)
                .build();
        Session session = new FreeSessionBuilder()
                .sessionStatus(SessionStatus.ONGOING)
                .build();

        Enrollment enrollment = session.requestEnroll(approvedStudent, payment);

        Assertions.assertThat(enrollment.getStatus()).isEqualTo(RequestStatus.REQUESTED);
        Assertions.assertThat(enrollment.getSessionId()).isEqualTo(payment.getSessionId());
        Assertions.assertThat(enrollment.getUserId()).isEqualTo(payment.getNsUserId());
    }

}
