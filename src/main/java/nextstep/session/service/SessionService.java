package nextstep.session.service;

import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.Student;
import nextstep.session.domain.StudentRepository;
import nextstep.session.dto.SessionRequestDto;
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

    public void saveSession(SessionRequestDto dto) {
        Session session = new Session(
                dto.getId(),
                dto.getStartAt(),
                dto.getEndAt(),
                dto.getCover(),
                dto.getSessionType(),
                dto.getSessionStatus(),
                dto.getCapacity(),
                dto.getStudents()
        );
        sessionRepository.save(session);
    }

    public void enrollSession(NsUser nsUser, Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(RuntimeException::new);
        Student enrollStudent = session.enroll(nsUser);
        studentRepository.save(enrollStudent);
    }
}
