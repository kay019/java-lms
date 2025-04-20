package nextstep.courses.service;

import nextstep.common.exception.UserNotFoundException;
import nextstep.courses.domain.EnrollmentHistory;
import nextstep.courses.domain.EnrollmentHistoryRepository;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;

import java.time.LocalDateTime;

public class EnrollmentService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final EnrollmentHistoryRepository enrollmentHistoryRepository;

    public EnrollmentService(SessionRepository sessionRepository, UserRepository userRepository, PaymentRepository paymentRepository, EnrollmentHistoryRepository enrollmentHistoryRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.enrollmentHistoryRepository = enrollmentHistoryRepository;
    }

    public void enroll(Long sessionId, String userId, Long paymentId) {
        Session session = sessionRepository.findById(sessionId);
        NsUser user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        Payment payment = paymentRepository.findById(paymentId);
        EnrollmentHistory enrollmentHistory = session.enroll(user, payment.getAmount(), LocalDateTime.now());
        enrollmentHistoryRepository.save(enrollmentHistory);
    }

}
