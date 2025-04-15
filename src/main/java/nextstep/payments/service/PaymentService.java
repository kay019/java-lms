package nextstep.payments.service;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void payment(Long sessionId, Long studentId, Long amount) {
        Payment payment = new Payment(sessionId, studentId, amount);
        paymentRepository.save(payment);
    }
}
