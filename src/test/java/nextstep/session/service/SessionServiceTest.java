package nextstep.session.service;

import nextstep.session.domain.Money;
import nextstep.session.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Test
    void registerSessionTest() {
        long sessionId = 1L;
        long amount = 5000;
        NsUser javajigi = NsUserTest.JAVAJIGI;
        Payment payment = sessionService.registerSession(sessionId, javajigi, amount);

        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getSessionId()).isEqualTo(sessionId);
        assertThat(payment.getNsUserId()).isEqualTo(javajigi.getId());
        assertThat(payment.getAmount()).isEqualTo(new Money(amount));
    }

}
