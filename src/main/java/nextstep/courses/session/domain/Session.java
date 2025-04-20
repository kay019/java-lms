package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;

public class Session {
    private final SessionDate sessionDate;
    private final SessionCoverImage sessionCoverImage;
    private final SessionStatus sessionStatus;
    private final Enrollments enrollments;
    private final SessionType sessionType;
    private final int maxParticipants;
    private final Long fee;

    private Session(SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus,
                    SessionType sessionType, int maxParticipants, Long fee, Enrollments enrollments) {
        this.sessionDate = sessionDate;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionStatus = sessionStatus;
        this.sessionType = sessionType;
        this.maxParticipants = maxParticipants;
        this.fee = fee;
        this.enrollments = enrollments;
    }

    public static Session free(SessionDate date, SessionCoverImage image, SessionStatus status, Enrollments enrollments) {
        return new Session(date, image, status, SessionType.FREE, 0, 0L, enrollments);
    }

    public static Session paid(SessionDate date, SessionCoverImage image, SessionStatus status, Enrollments enrollments, int maxParticipants, Long fee) {
        return new Session(date, image, status, SessionType.PAID, maxParticipants, fee, enrollments);
    }


    public void enroll(Payment payment, NsUser nsUser) {
        if (!sessionStatus.canEnroll()) {
            throw new IllegalStateException("모집 중인 상태에서만 수강 신청이 가능합니다.");
        }
        sessionType.getStrategy().validate(enrollments, payment, maxParticipants, fee);
        Enrollment enrollment = new Enrollment(this, nsUser, payment);
        enrollments.add(enrollment);
    }
}
