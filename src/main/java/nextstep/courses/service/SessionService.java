package nextstep.courses.service;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void addStudentAndSaveSession(Session session) {
        session.addStudent();
        sessionRepository.save(session);
    }

    public Session findSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }
}
