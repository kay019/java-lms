package nextstep.session.service;

import nextstep.courses.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.Money;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final PaymentService paymentService;

    public SessionService(SessionRepository sessionRepository,
                          PaymentService paymentService) {
        this.sessionRepository = sessionRepository;
        this.paymentService = paymentService;
    }

    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Payment registerSession(long sessionId, NsUser nsUser, long amount) {
        Session session = findById(sessionId);
        int currentStudentCount = paymentService.getBySessionId(sessionId).getSize();
        Payment payment = session.register(nsUser, new Money(amount), currentStudentCount);
        paymentService.save(payment);
        return payment;
    }
}
