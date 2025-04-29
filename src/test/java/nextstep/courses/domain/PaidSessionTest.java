package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.support.builder.PaidSessionBuilder;
import nextstep.support.builder.PaymentBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaidSessionTest {

    @Test
    @DisplayName("강의 최대 수강 인원을 초과할 수 없다.")
    void validateEnrollTest_validateMaxStudent() {
        int fee = 10_000;
        int maxStudent = 1;
        Payment payment = new PaymentBuilder()
                .amount((long) fee)
                .nsUserId(1L)
                .build();
        Session session = new PaidSessionBuilder()
                .fee(fee)
                .maxStudent(maxStudent)
                .enrollStatus(EnrollStatus.RECRUITING)
                .sessionStatus(SessionStatus.ONGOING)
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    session.requestEnroll(maxStudent, payment);
                })
                .withMessageContaining("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    void validateEnrollTest_validateSameFee() {
        int fee = 10_000;
        int maxStudent = 1;
        int approvedStudent = 0;
        Payment payment = new PaymentBuilder()
                .amount((long) fee - 10)
                .nsUserId(1L)
                .build();
        Session session = new PaidSessionBuilder()
                .fee(fee)
                .maxStudent(maxStudent)
                .enrollStatus(EnrollStatus.RECRUITING)
                .sessionStatus(SessionStatus.ONGOING)
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    session.requestEnroll(approvedStudent, payment);
                })
                .withMessageContaining("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
    }

    @Test
    @DisplayName("수강 신청을 하면 수강 대기 상태가 된다.")
    void enrollStudentTest() {
        int fee = 10_000;
        int maxStudent = 3;
        int approvedStudent = 1;
        Payment payment = new PaymentBuilder()
                .amount((long) fee)
                .nsUserId(1L)
                .build();
        Session session = new PaidSessionBuilder()
                .fee(fee)
                .maxStudent(maxStudent)
                .enrollStatus(EnrollStatus.RECRUITING)
                .sessionStatus(SessionStatus.ONGOING)
                .build();

        Enrollment enrollment = session.requestEnroll(approvedStudent, payment);

        assertEquals(enrollment.getStatus(), RequestStatus.REQUESTED);
        assertEquals(enrollment.getSessionId(), payment.getSessionId());
        assertEquals(enrollment.getUserId(), payment.getNsUserId());
    }

}
