package nextstep.courses.service;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.users.domain.NsUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Test
    public void findByIdTest() {
        Session session = sessionService.findById(1);
        List<Payment> payments = session.getPayments().getPayments();

        assertThat(session.getId()).isEqualTo(1);
        assertThat(session.getCourse().getId()).isEqualTo(1);
        assertThat(session.getCoverImage().getId()).isEqualTo(1);
        assertThat(payments.size()).isEqualTo(2);
        assertThat(payments.get(0).getId()).isEqualTo(2);
        assertThat(payments.get(1).getId()).isEqualTo(1);
    }

}
