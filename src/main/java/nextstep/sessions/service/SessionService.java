package nextstep.sessions.service;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionRepository;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.Student;
import nextstep.sessions.domain.StudentRepository;
import nextstep.sessions.domain.cover.SessionCover;
import nextstep.sessions.domain.type.SessionType;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    private final StudentRepository studentRepository;

    public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
    }

    public void saveSession(Long id, LocalDateTime startAt, LocalDateTime endAt, SessionCover cover,
                            SessionType sessionType, SessionStatus sessionStatus, Long capacity,
                            List<Student> students) {
        Session session = new Session(id, startAt, endAt, cover, sessionType, sessionStatus, capacity, students);
        sessionRepository.save(session);
    }

    public void enrollSession(NsUser nsUser, Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(RuntimeException::new);
        Student enrollStudent = session.enroll(nsUser);
        studentRepository.save(enrollStudent);
    }
}
