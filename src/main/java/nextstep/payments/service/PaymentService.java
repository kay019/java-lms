package nextstep.payments.service;

import nextstep.courses.domain.CourseSession;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAllPayment(final CourseSession session, final NsUser user) {
        return paymentRepository.findAllBySessionIdAndUserId(session.getId(), user.getId());
    }
} 
