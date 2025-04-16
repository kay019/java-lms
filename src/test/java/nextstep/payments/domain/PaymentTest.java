package nextstep.payments.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class PaymentTest {
    @Test
    void isSameAmountAsTest() {
        Payment payment = new Payment("id", 1L, 1L, 10000L);

        assertAll(() -> assertThat(payment.isSameAmountAs(10001L)).isFalse(),
                () -> assertThat(payment.isSameAmountAs(9999L)).isFalse(),
                () -> assertThat(payment.isSameAmountAs(10000L)).isTrue());
    }
}
