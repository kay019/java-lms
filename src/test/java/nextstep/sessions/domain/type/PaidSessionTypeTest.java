package nextstep.sessions.domain.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaidSessionTypeTest {
    @Test
    void createTest() {
        PaidSessionType paidSessionType = new PaidSessionType(10L, 10000L);

        assertThat(paidSessionType).isEqualTo(new PaidSessionType(10L, 10000L));
    }

    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    @Test
    void validatePaymentTest() {
        PaidSessionType paidSessionType = new PaidSessionType(10L, 10000L);
        Payment payment = new Payment("1", 1L, 1L, 9999L);

        assertThatThrownBy(() -> paidSessionType.isRegisterable(payment, 10L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("payment amount is not same as session amount");
    }
}
