package nextstep.courses.service;

import nextstep.courses.domain.Enrollment;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.image.CoverImage;
import nextstep.courses.domain.repository.CoverImageRepository;
import nextstep.courses.domain.repository.EnrollmentRepository;
import nextstep.courses.domain.repository.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final CoverImageRepository coverImageRepository;
    private final EnrollmentRepository enrollmentRepository;

    public SessionService(SessionRepository sessionRepository, CoverImageRepository coverImageRepository, EnrollmentRepository enrollmentRepository) {
        this.sessionRepository = sessionRepository;
        this.coverImageRepository = coverImageRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Session findSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("존재하지 않는 강의 입니다. sessionId : %d", sessionId)
                ));
    }

    public Enrollment findEnrollmentById(Long sessionId, Long userId) {
        return enrollmentRepository.findById(sessionId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("수강 신청 이력이 없습니다. sessionId : %d, userId : %d", sessionId, userId)
                ));
    }

    public List<CoverImage> findAllBySessionId(Long sessionId) {
        Session session = findSessionById(sessionId);
        return coverImageRepository.findByAllSessionId(sessionId);
    }

    @Transactional
    public void requestEnroll(Payment payment) {
        Long sessionId = payment.getSessionId();
        Session session = findSessionById(sessionId);
        int approvedStudent = getApprovedStudentNumber(sessionId);
        Enrollment enrollment = session.requestEnroll(approvedStudent, payment);
        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void acceptEnroll(Long sessionId, Long userId) {
        Session session = findSessionById(sessionId);
        Enrollment enrollment = findEnrollmentById(sessionId, userId);
        enrollment.approve();
    }

    @Transactional
    public void rejectEnroll(Long sessionId, Long userId) {
        Session session = findSessionById(sessionId);
        Enrollment enrollment = findEnrollmentById(sessionId, userId);
        enrollment.reject();
    }

    private int getApprovedStudentNumber(Long sessionId) {
        return enrollmentRepository.countApprovedBySessionId(sessionId);
    }

}
