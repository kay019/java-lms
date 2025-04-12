package nextstep.sessionstudent;

import org.springframework.stereotype.Service;

import nextstep.users.domain.NsUser;

@Service
public class SessionStudentService {

    private final SessionStudentRepository sessionStudentRepository;

    public SessionStudentService(SessionStudentRepository sessionStudentRepository) {
        this.sessionStudentRepository = sessionStudentRepository;
    }

    public SessionStudent findById(long id) {
        return sessionStudentRepository.findById(id);
    }

    public SessionStudents findBySessionId(long sessionId) {
        return sessionStudentRepository.findBySessionId(sessionId);
    }

    public SessionStudent save(SessionStudent sessionStudent) {
        sessionStudentRepository.save(sessionStudent);
        return sessionStudent;
    }

}
