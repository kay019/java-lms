package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.support.builder.PaymentBuilder;
import nextstep.support.builder.SessionBuilder;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionTest {
    @Test
    @DisplayName("수강을 완료하면 수강 학생 목록에 추가한다.")
    void enrollStudentTest() {
        int fee = 10_000;
        int maxStudent = 1;
        Payment payment = new PaymentBuilder()
                .amount((long) fee)
                .nsUser(NsUserTest.JAVAJIGI)
                .build();
        Session session = new SessionBuilder()
                .paid(fee, maxStudent)
                .status(SessionStatus.RECRUITING)
                .build();

        session.enroll(payment);

        assertEquals(session.getStudentSize(), 1);
    }
}
