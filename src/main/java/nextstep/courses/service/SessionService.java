package nextstep.courses.service;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionEnrollment;
import nextstep.courses.domain.session.SessionEnrollmentRepository;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;

@Service("sessionService")
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionEnrollmentRepository sessionEnrollmentRepository;

    public SessionService(SessionRepository sessionRepository, SessionEnrollmentRepository sessionEnrollmentRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionEnrollmentRepository = sessionEnrollmentRepository;
    }

    public void enrollRequest(Session targetSession, Payment payment) {
        SessionEnrollment enroll = targetSession.enroll(payment);
        if (enroll.isAvailable()) {
            sessionEnrollmentRepository.save(enroll);
        } else {
            throw new IllegalArgumentException("수강신청이 불가능한 상태입니다.");
        }
    }

    public void approveEnroll(SessionEnrollment sessionEnrollment) {
        Session session = sessionRepository.findById(sessionEnrollment.getSessionId());
        if (session.enrollmentCountOver(session.getMaxEnrollment())) {
            throw new IllegalArgumentException("정원 초과로 수강신청이 불가능합니다.");
        }
        session.approveEnroll();
        sessionEnrollment.approve();
        sessionEnrollmentRepository.save(sessionEnrollment);
    }

    public void rejectEnroll(SessionEnrollment sessionEnrollment) {
        sessionEnrollment.reject();
        sessionEnrollmentRepository.save(sessionEnrollment);
    }


}
