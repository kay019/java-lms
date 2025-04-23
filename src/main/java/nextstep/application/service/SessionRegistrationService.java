package nextstep.application.service;

import nextstep.courses.domain.Session;
import nextstep.courses.service.SessionService;
import nextstep.courses.domain.Student;
import nextstep.courses.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionRegistrationService {
    private final SessionService sessionService;
    private final StudentService studentService;

    public SessionRegistrationService(SessionService sessionService, StudentService studentService) {
        this.sessionService = sessionService;
        this.studentService = studentService;
    }

    @Transactional
    public ResponseEntity<String> register(Long studentId, Long sessionId) {
        Student student = studentService.findStudentById(studentId);
        Session session = sessionService.findSessionById(sessionId);
        session.registerStudent(student);
        return ResponseEntity.ok("수강 신청이 완료되었습니다.");
    }
}
