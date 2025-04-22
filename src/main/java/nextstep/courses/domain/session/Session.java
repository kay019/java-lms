package nextstep.courses.domain.session;

import java.time.LocalDate;

import nextstep.courses.domain.session.inner.EnrollmentStatus;
import nextstep.courses.domain.session.inner.SessionDate;
import nextstep.courses.domain.session.inner.SessionStatus;
import nextstep.courses.domain.session.inner.SessionType;
import nextstep.payments.domain.Payment;

public class Session {

    private Long id;

    private Long courseId;

    private String title;

    private SessionType sessionType;

    private SessionDate sessionDate;

    private SessionStatus status;

    private EnrollmentStatus enrollmentStatus;

    private int enrollmentCount;

    public Session(Long id, Long courseId, String title, SessionType sessionType, LocalDate startDate,
            LocalDate finishDate, SessionStatus status, EnrollmentStatus enrollmentStatus, int enrollmentCount) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.sessionType = sessionType;
        this.sessionDate = new SessionDate(startDate, finishDate);
        this.status = status;
        this.enrollmentStatus = enrollmentStatus;
        this.enrollmentCount = enrollmentCount;
    }

    public boolean enrollmentCountOver(int enrollmentCount) {
        return this.enrollmentCount >= enrollmentCount;
    }

    public SessionEnrollment enroll(Payment payment) {
        if (enrollmentStatus.canEnroll() && sessionType.enroll(payment, this)) {
//            enrollUser(payment.getNsUserId());
            return SessionEnrollment.requestEnroll(id, payment.getNsUserId());
        }

        return SessionEnrollment.notAvailableEnroll(id, payment.getNsUserId());
    }

    public void approveEnroll() {
        enrollmentCount++;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getSessionType() {
        return sessionType.getSessionType();
    }

    public int getMaxEnrollment() {
        return sessionType.getMaxEnrollment();
    }

    public long getFee() {
        return sessionType.getFee();
    }

    public LocalDate getStartDate() {
        return sessionDate.startDate();
    }

    public LocalDate getFinishDate() {
        return sessionDate.finishDate();
    }

    public String getStatus() {
        return status.name();
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus.name();
    }

    public int getEnrollmentCount() {
        return enrollmentCount;
    }
}
