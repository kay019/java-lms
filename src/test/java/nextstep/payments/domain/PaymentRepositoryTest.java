package nextstep.payments.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.session.domain.Money;
import nextstep.payments.infrastructure.JdbcPaymentRepository;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class PaymentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new JdbcPaymentRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Payment payment
            = new Payment(12391283L, NsUserTest.JAVAJIGI.getId(), new Money(1000));

        int count = paymentRepository.save(payment);
        Payment newPayment = paymentRepository.findById(payment.getId());

        assertThat(count).isEqualTo(1);
        assertThat(newPayment.getId()).isEqualTo(payment.getId());
        assertThat(newPayment.getSessionId()).isEqualTo(payment.getSessionId());
        assertThat(newPayment.getNsUserId()).isEqualTo(payment.getNsUserId());
        assertThat(newPayment.getAmount()).isEqualTo(payment.getAmount());
    }

}
