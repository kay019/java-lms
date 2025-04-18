package nextstep.payments.service;

import org.springframework.stereotype.Service;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.domain.Payments;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payments getBySessionId(long sessionId) {
        return new Payments(paymentRepository.findBySessionId(sessionId));
    }

    public int save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
