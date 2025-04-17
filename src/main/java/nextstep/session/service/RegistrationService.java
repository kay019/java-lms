package nextstep.session.service;

import nextstep.session.domain.Money;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.sessionstudent.SessionStudent;
import nextstep.sessionstudent.SessionStudentRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final SessionRepository sessionRepository;
    private final SessionStudentRepository sessionStudentRepository;

    public RegistrationService(SessionRepository sessionRepository, SessionStudentRepository sessionStudentRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionStudentRepository = sessionStudentRepository;
    }

    public SessionStudent registerSession(long sessionId, NsUser nsUser, long amount) {
        Session session = sessionRepository.findById(sessionId);
        int currStudentCount = sessionStudentRepository
            .findBySessionId(sessionId)
            .filterApproved()
            .getSize();

        SessionStudent sessionStudent
            = session.register(nsUser, new Money(amount), currStudentCount);

        sessionStudentRepository.save(sessionStudent);

        return sessionStudent;
    }

}
