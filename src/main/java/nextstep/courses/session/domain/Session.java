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
    private final EnrollStatus enrollStatus;
    private final Enrollments enrollments;
    private final SessionType sessionType;
    private final int maxParticipants;
    private final Long fee;

    public Session(Long id, Long courseId, SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus,
                   EnrollStatus enrollStatus, SessionType sessionType, int maxParticipants, Long fee, Enrollments enrollments) {
        this.id = id;
        this.courseId = courseId;
        this.sessionDate = sessionDate;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionStatus = sessionStatus;
        this.enrollStatus = enrollStatus;
        this.sessionType = sessionType;
        this.maxParticipants = maxParticipants;
        this.fee = fee;
        this.enrollments = enrollments;
    }

    public static Session free(Long id, Long courseId, SessionDate date, SessionCoverImage image, SessionStatus status, EnrollStatus enrollStatus, Enrollments enrollments) {
        return new Session(id, courseId, date, image, status, enrollStatus, SessionType.FREE, 0, 0L, enrollments);
    }

    public static Session paid(Long id, Long courseId, SessionDate date, SessionCoverImage image, SessionStatus status, EnrollStatus enrollStatus, Enrollments enrollments, int maxParticipants, Long fee) {
        return new Session(id, courseId, date, image, status, enrollStatus, SessionType.PAID, maxParticipants, fee, enrollments);
    }


    public void enroll(Payment payment, NsUser nsUser) {
        if (sessionStatus.canNotEnroll()) {
            throw new IllegalArgumentException("종료된 강의는 수강신청할 수 없습니다.");
        }

        if(enrollStatus != null && enrollStatus.canNotEnroll()){ // 변경 사항 이전 데이터의 경우 null 이므로
            throw new IllegalArgumentException("모집 중인 상태에서만 수강 신청이 가능합니다.");
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

    public EnrollStatus getEnrollStatus() {
        return enrollStatus;
    }

    public Long getFee() {
        return fee;
    }
}
