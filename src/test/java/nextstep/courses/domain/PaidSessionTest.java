package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.support.builder.PaidSessionBuilder;
import nextstep.support.builder.PaymentBuilder;
import nextstep.users.domain.NsUserTest;
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
        Payment payment1 = new PaymentBuilder()
                .amount((long) fee)
                .nsUser(NsUserTest.JAVAJIGI)
                .build();
        Payment payment2 = new PaymentBuilder()
                .amount((long) fee)
                .nsUser(NsUserTest.SANJIGI)
                .build();
        Session session = new PaidSessionBuilder()
                .fee(fee)
                .maxStudent(maxStudent)
                .status(SessionStatus.RECRUITING)
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    session.enroll(payment1);
                    session.enroll(payment2);
                })
                .withMessageContaining("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    void validateEnrollTest_validateSameFee() {
        int fee = 10_000;
        int maxStudent = 1;
        Payment payment1 = new PaymentBuilder()
                .amount((long) fee - 10)
                .nsUser(NsUserTest.JAVAJIGI)
                .build();
        Session session = new PaidSessionBuilder()
                .fee(fee)
                .maxStudent(maxStudent)
                .status(SessionStatus.RECRUITING)
                .build();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    session.enroll(payment1);
                })
                .withMessageContaining("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
    }

    @Test
    @DisplayName("수강을 완료하면 수강 학생 목록에 추가한다.")
    void enrollStudentTest() {
        int fee = 10_000;
        int maxStudent = 3;
        Payment payment1 = new PaymentBuilder()
                .amount((long) fee)
                .nsUser(NsUserTest.JAVAJIGI)
                .build();
        Payment payment2 = new PaymentBuilder()
                .amount((long) fee)
                .nsUser(NsUserTest.SANJIGI)
                .build();
        Session session = new PaidSessionBuilder()
                .fee(fee)
                .maxStudent(maxStudent)
                .status(SessionStatus.RECRUITING)
                .build();

        session.enroll(payment1);
        session.enroll(payment2);

        assertEquals(session.getStudentSize(), 2);
    }

}
