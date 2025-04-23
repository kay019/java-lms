package nextstep.session.domain;

import static nextstep.session.domain.EnrollmentStatus.ENROLLING;
import static nextstep.session.domain.SessionProgressStatus.CLOSED;

public class SessionStatus {
    private final SessionProgressStatus progressStatus;
    private final EnrollmentStatus enrollmentStatus;

    public SessionStatus(SessionProgressStatus progressStatus, EnrollmentStatus enrollmentStatus) {
        this.progressStatus = progressStatus;
        this.enrollmentStatus = enrollmentStatus;
    }

    public SessionStatus(String progressStatus, String enrollmentStatus) {
        this(SessionProgressStatus.valueOf(progressStatus), EnrollmentStatus.valueOf(enrollmentStatus));
    }

    public String getProgressStatusName() {
        return progressStatus.name();
    }

    public String getEnrollmentStatusName() {
        return enrollmentStatus.name();
    }

    public boolean isEnrollmentAvailable() {
        return enrollmentStatus == ENROLLING
            && progressStatus != CLOSED;
    }

    public boolean isEnrollmentUnavailable() {
        return !isEnrollmentAvailable();
    }
}
