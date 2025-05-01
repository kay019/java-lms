package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    public FreeSession(Long id, SessionStatus sessionStatus, EnrollStatus enrollStatus,
                       SessionDate date, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, sessionStatus, enrollStatus, date, createdAt, updatedAt);
    }

    public FreeSession(Long id, SessionStatus sessionStatus,
                       EnrollStatus enrollStatus, SessionDate date) {
        super(id, sessionStatus, enrollStatus, date);
    }

    @Override
    public Enrollment requestEnroll(int approvedStudent, Payment payment) {
        enrollStatus.validateEnroll();
        return Enrollment.requestEnroll(payment.getSessionId(), payment.getNsUserId());
    }

}
