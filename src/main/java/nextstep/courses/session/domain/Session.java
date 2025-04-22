package nextstep.courses.session.domain;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.Enrollments;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private Long id;
    private final Long courseId;
    private final SessionDate sessionDate;
    private final SessionCoverImage sessionCoverImage;
    private final SessionStatus sessionStatus;
    private final Enrollments enrollments;
    private final SessionType sessionType;
    private final int maxParticipants;
    private final Long fee;

    public Session(Long id, Long courseId, SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus,
                   SessionType sessionType, int maxParticipants, Long fee, Enrollments enrollments) {
        this.id = id;
        this.courseId = courseId;
        this.sessionDate = sessionDate;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionStatus = sessionStatus;
        this.sessionType = sessionType;
        this.maxParticipants = maxParticipants;
        this.fee = fee;
        this.enrollments = enrollments;
    }

    public static Session free(Long id, Long courseId, SessionDate date, SessionCoverImage image, SessionStatus status, Enrollments enrollments) {
        return new Session(id, courseId, date, image, status, SessionType.FREE, 0, 0L, enrollments);
    }

    public static Session paid(Long id, Long courseId, SessionDate date, SessionCoverImage image, SessionStatus status, Enrollments enrollments, int maxParticipants, Long fee) {
        return new Session(id, courseId, date, image, status, SessionType.PAID, maxParticipants, fee, enrollments);
    }


    public void enroll(Payment payment, NsUser nsUser) {
        if (sessionStatus.canNotEnroll()) {
            throw new IllegalArgumentException("종료된 강의는 수강신청할 수 없습니다.");
        }
        sessionType.getStrategy().validate(enrollments, payment, maxParticipants, fee);
        Enrollment enrollment = new Enrollment(id, nsUser, payment);
        enrollments.add(enrollment);
    }

    public Long getCourseId() {
        return courseId;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public SessionCoverImage getSessionCoverImage() {
        return sessionCoverImage;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public Long getFee() {
        return fee;
    }
}
