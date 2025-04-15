package nextstep.payments.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    public static final Payment DEFAULT_PAYMENT = new Payment("1", 1L, 1L, 1000L);

    @Test
    @DisplayName("equals")
    void equals() {
        assertThat(DEFAULT_PAYMENT.equals(1000L)).isTrue();
    }
}