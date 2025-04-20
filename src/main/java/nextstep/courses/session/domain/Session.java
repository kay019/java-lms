package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;

public class Session {
    private final SessionDate sessionDate;
    private final SessionCoverImage sessionCoverImage;
    private final SessionStatus sessionStatus;
    final Enrollments enrollments;

    public Session (SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus) {
        this(sessionDate, sessionCoverImage, sessionStatus, new Enrollments(new ArrayList<>()));
    }

    public Session (SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus, Enrollments enrollments) {
        this.sessionDate = sessionDate;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionStatus = sessionStatus;
        this.enrollments = enrollments;
    }

    public void enroll(Payment payment, NsUser nsUser) {
        if (!sessionStatus.canEnroll()) {
            throw new IllegalStateException("모집 중인 상태에서만 수강 신청이 가능합니다.");
        }
        preEnrollValidation(payment, nsUser);
        Enrollment enrollment = new Enrollment(this, nsUser, payment);
        enrollments.add(enrollment);
    }

    protected void preEnrollValidation(Payment payment, NsUser nsUser) {
    }
}
