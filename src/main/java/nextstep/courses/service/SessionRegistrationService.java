package nextstep.courses.service;

import nextstep.courses.domain.*;
import nextstep.courses.infrastructure.CourseSessionRepository;
import nextstep.courses.infrastructure.RegistrationRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import nextstep.users.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionRegistrationService {

    private final CourseSessionRepository sessionRepository;
    private final RegistrationRepository registrationRepository;
    private final PaymentService paymentService;
    private final UserService userService;
    
    public SessionRegistrationService(final CourseSessionRepository sessionRepository,
                                      final RegistrationRepository registrationRepository,
                                      final PaymentService paymentService,
                                      final UserService userService) {
        this.sessionRepository = sessionRepository;
        this.registrationRepository = registrationRepository;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @Transactional
    public Registration registerForSession(final Long sessionId, final String userId) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("세션을 찾을 수 없습니다"));

        NsUser user = userService.findById(userId);
        List<Payment> payments = paymentService.findAllPayment(session, user);
        Registration registration = session.addRegistration(session, user, payments);

        return registrationRepository.save(registration);
    }
} 
