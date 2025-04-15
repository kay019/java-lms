package nextstep.courses.service;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;

public class EnrollService {

    private final SessionRepository sessionRepository;

    public EnrollService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void enroll(Long sessionId, Long userId, Payment payment) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        session.enroll(userId, payment);
    }
}
