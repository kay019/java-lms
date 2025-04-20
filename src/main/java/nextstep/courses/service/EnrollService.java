package nextstep.courses.service;

import org.springframework.transaction.annotation.Transactional;

import nextstep.courses.domain.Participant;
import nextstep.courses.domain.ParticipantRepository;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;

public class EnrollService {

    private final SessionRepository sessionRepository;

    private final ParticipantRepository participantRepository;

    public EnrollService(SessionRepository sessionRepository, ParticipantRepository participantRepository) {
        this.sessionRepository = sessionRepository;
        this.participantRepository = participantRepository;
    }

    @Transactional
    public void enroll(Long sessionId, Long userId, Payment payment) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        Participant participant = session.enroll(userId, payment);
        participantRepository.save(sessionId, participant);
    }
}
