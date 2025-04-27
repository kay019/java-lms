package nextstep.session.service;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.Student;
import nextstep.session.domain.StudentRepository;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.SessionType;
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
