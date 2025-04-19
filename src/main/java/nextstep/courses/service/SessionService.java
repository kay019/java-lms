package nextstep.courses.service;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionDescriptor;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.factory.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

@Service("sessionService")
public class SessionService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    public void createSession(Long courseId, SessionConstraint constraint, SessionDescriptor descriptor) {
        Session newSession = new Session(constraint, descriptor);
        sessionRepository.save(newSession.toSessionEntity(courseId));
    }

    @Transactional
    public void deleteSession(long sessionId) throws IOException {
        SessionFactory sessionFactory = new SessionFactory();
        Session session = sessionFactory.create(sessionRepository.findById(sessionId));
        session.delete();
    }
}
