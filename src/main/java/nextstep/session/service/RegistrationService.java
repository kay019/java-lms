package nextstep.session.service;

import nextstep.session.domain.Money;
import nextstep.session.domain.Session;
import nextstep.sessionstudent.SessionStudent;
import nextstep.sessionstudent.SessionStudentService;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final SessionService sessionService;
    private final SessionStudentService sessionStudentService;

    public RegistrationService(SessionService sessionService, SessionStudentService sessionStudentService) {
        this.sessionService = sessionService;
        this.sessionStudentService = sessionStudentService;
    }

    public SessionStudent registerSession(long sessionId, NsUser nsUser, long amount) {
        Session session = sessionService.findById(sessionId);
        int currStudentCount = sessionStudentService
            .findBySessionId(sessionId)
            .filterApproved()
            .getSize();

        SessionStudent sessionStudent
            = session.register(nsUser, new Money(amount), currStudentCount);

        sessionStudentService.save(sessionStudent);

        return sessionStudent;
    }

}
