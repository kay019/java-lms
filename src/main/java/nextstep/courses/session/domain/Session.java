package nextstep.courses.session.domain;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.Enrollments;
import nextstep.courses.session.domain.coverImages.SessionCoverImage;
import nextstep.courses.session.domain.coverImages.SessionCoverImages;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private final Long id;
    private final Long courseId;
    private final SessionDate sessionDate;
    private final SessionCoverImages sessionCoverImages;
    private final SessionCoverImage sessionCoverImage;
    private final SessionStatus sessionStatus;
    private final EnrollStatus enrollStatus;
    private final Enrollments enrollments;
    private final SessionType sessionType;
    private final int maxParticipants;
    private final Long fee;

    public Session(Long id, Long courseId, SessionDate sessionDate, SessionCoverImages sessionCoverImages, SessionStatus sessionStatus,
                   EnrollStatus enrollStatus, SessionType sessionType, int maxParticipants, Long fee, Enrollments enrollments) {
        this.id = id;
        this.courseId = courseId;
        this.sessionDate = sessionDate;
        this.sessionCoverImages = sessionCoverImages;
        this.sessionCoverImage = sessionCoverImages.mainImage();
        this.sessionStatus = sessionStatus;
        this.enrollStatus = enrollStatus;
        this.sessionType = sessionType;
        this.maxParticipants = maxParticipants;
        this.fee = fee;
        this.enrollments = enrollments;
    }

    // 커버 이미지들 주입용 생성자
    public Session withImages(SessionCoverImages images) {
        return new Session(
                this.id,
                this.courseId,
                this.sessionDate,
                images,
                this.sessionStatus,
                this.enrollStatus,
                this.sessionType,
                this.maxParticipants,
                this.fee,
                this.enrollments
        );
    }

    public static Session free(Long id, Long courseId, SessionDate date, SessionCoverImages images, SessionStatus status, EnrollStatus enrollStatus, Enrollments enrollments) {
        return new Session(id, courseId, date, images, status, enrollStatus, SessionType.FREE, 0, 0L, enrollments);
    }

    public static Session paid(Long id, Long courseId, SessionDate date, SessionCoverImages images, SessionStatus status, EnrollStatus enrollStatus, Enrollments enrollments, int maxParticipants, Long fee) {
        return new Session(id, courseId, date, images, status, enrollStatus, SessionType.PAID, maxParticipants, fee, enrollments);
    }


    public void enrollToSelectedCourse(Payment payment, NsUser user) {
        validate(payment);
        Enrollment enrollment = Enrollment.enrollToSelectedCourse(this.id, user, payment);
        enrollments.add(enrollment);
    }

    public void enrollToGeneralCourse(Payment payment, NsUser user) {
        validate(payment);
        Enrollment enrollment = Enrollment.enrollToGeneralCourse(this.id, user, payment);
        enrollments.add(enrollment);
    }

    private void validate(Payment payment) {
        if (sessionStatus.canNotEnroll()) {
            throw new IllegalArgumentException("종료된 강의는 수강신청할 수 없습니다.");
        }
        if (enrollStatus != null && enrollStatus.canNotEnroll()) { // 변경 사항 이전 데이터의 경우 null 이므로
            throw new IllegalArgumentException("모집 중인 상태에서만 수강 신청이 가능합니다.");
        }
        sessionType.getStrategy().validate(enrollments, payment, maxParticipants, fee);
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
