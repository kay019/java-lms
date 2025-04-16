package nextstep.payments.service;

import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.Session;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.domain.Payments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("paymentService")
public class PaymentService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "paymentRepository")
    private PaymentRepository paymentRepository;

    public Payment payment(String id) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment();
    }

    public boolean save(String newPaymentId, long sessionId) {
        Payment newPayment = payment(newPaymentId);
        Session session = sessionRepository.findById(sessionId);
        Payments sessionPayments = new Payments(paymentRepository.findBySession(sessionId));
        if (sessionPayments.canEnroll(session, newPayment)) {
            paymentRepository.save(newPayment);
            return true;
        }
        return false;
    }
}
