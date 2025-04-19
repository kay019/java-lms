package nextstep.payments.factory;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionDescriptor;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.image.SessionImageType;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import nextstep.payments.domain.PaymentEntityUserMap;
import nextstep.stub.TestImageHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaymentsFactoryTest {

    @DisplayName("Payment DB 정보 및 User, Session 정보로 Payments 인스턴스 생성")
    @Test
    public void testCreate() throws IOException {
        PaymentsFactory paymentsFactory = new PaymentsFactory(new PaymentFactory());

        PaymentEntityUserMap paymentEntityUserMap = new PaymentEntityUserMap();

        SessionConstraint constraint = new SessionConstraint(100, 80);
        SessionDescriptor descriptor = new SessionDescriptor(
            new SessionImage("http://test", new TestImageHandler(300, 200, 1024L * 866L), SessionImageType.JPEG),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new SessionEnrollPolicy()
        );
        Session session = new Session("1", constraint, descriptor);

        assertDoesNotThrow(() -> paymentsFactory.create(session, paymentEntityUserMap));
    }
}
