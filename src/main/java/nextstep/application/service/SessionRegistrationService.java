package nextstep.application.service;

import nextstep.courses.domain.Session;
import nextstep.courses.service.SessionService;
import nextstep.students.service.StudentService;
import nextstep.payments.service.PaymentService;
import nextstep.students.domain.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionRegistrationService {
    private final SessionService sessionService;
    private final StudentService studentService;
    private final PaymentService paymentService;

    public SessionRegistrationService(SessionService sessionService, StudentService studentService, PaymentService paymentService) {
        this.sessionService = sessionService;
        this.studentService = studentService;
        this.paymentService = paymentService;
    }

    // 수업 등록 로직
    public ResponseEntity<String> register(Long studentId, Long sessionId) {
        Student student = studentService.findStudentById(studentId);
        Session session = sessionService.findSessionById(sessionId);
        session.validateEnrollment(student.getBudget());
        sessionService.addStudentAndSaveSession(session);
        paymentService.payment(sessionId, studentId, session.getFee().longValue());
        studentService.addSessionAndSaveStudent(student, sessionId);
        return ResponseEntity.ok("수업 등록이 완료되었습니다.");
    }
}
