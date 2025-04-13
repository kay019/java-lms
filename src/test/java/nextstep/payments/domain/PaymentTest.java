package nextstep.payments.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentTest {

  @Test
  void 같은금액인지_확인() {
    Payment payment = new Payment("1", null, null, 1000L);

    assertTrue(payment.isSameAmount(1000L));
  }
}