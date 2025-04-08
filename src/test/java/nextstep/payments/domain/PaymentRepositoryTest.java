package nextstep.payments.domain;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Money;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionBuilder;
import nextstep.payments.record.PaymentRecord;
import nextstep.payments.infrastructure.JdbcPaymentRepository;
import nextstep.payments.record.PaymentRecords;
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
        long sessionId = 123892L;
        Money sessionFee = new Money(10000);

        Session session = new SessionBuilder()
            .id(sessionId)
            .paid(sessionFee.getAmount(), 10)
            .build();

        Payments payments = new Payments();
        payments.add(new Payment(session, NsUserTest.JAVAJIGI, sessionFee));
        payments.add(new Payment(session, NsUserTest.SANJIGI, sessionFee));

        PaymentRecords paymentRecords = PaymentRecord.from(payments);

        for (PaymentRecord paymentRecord : paymentRecords.getRecords()) {
            int count = paymentRepository.save(paymentRecord);
            PaymentRecord newPaymentRecord = paymentRepository.findById(paymentRecord.getId());

            assertThat(count).isEqualTo(1);
            assertThat(newPaymentRecord.getId()).isEqualTo(paymentRecord.getId());
            assertThat(newPaymentRecord.getSessionId()).isEqualTo(sessionId);
            assertThat(newPaymentRecord.getNsUserId()).isEqualTo(paymentRecord.getNsUserId());
            assertThat(newPaymentRecord.getAmount()).isEqualTo(sessionFee.getAmount());
            assertThat(newPaymentRecord.getCreatedAt()).isEqualTo(paymentRecord.getCreatedAt());
        }
    }

}
