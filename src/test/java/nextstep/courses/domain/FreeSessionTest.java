package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.support.builder.FreeSessionBuilder;
import nextstep.support.builder.PaymentBuilder;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeSessionTest {

    @Test
    @DisplayName("수강을 완료하면 수강 학생 목록에 추가한다.")
    void enrollStudentTest() {
        Payment payment = new PaymentBuilder()
                .amount(0L)
                .nsUser(NsUserTest.JAVAJIGI)
                .build();
        Session session = new FreeSessionBuilder()
                .sessionStatus(SessionStatus.ONGOING)
                .build();

        session.enroll(payment);

        assertEquals(session.getStudentSize(), 1);
    }

}
