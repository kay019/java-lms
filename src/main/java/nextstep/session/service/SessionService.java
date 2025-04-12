package nextstep.session.service;

import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }
}
