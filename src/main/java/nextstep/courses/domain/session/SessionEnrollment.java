package nextstep.courses.domain.session;

import java.util.Objects;
import nextstep.courses.domain.session.inner.UserEnrollmentStatus;

public class SessionEnrollment {

    private Long id;
    private Long sessionId;
    private Long nsUserId;
    private UserEnrollmentStatus status;

    public SessionEnrollment(Long id, Long sessionId, Long nsUserId, UserEnrollmentStatus status) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.status = status;
    }

    public static SessionEnrollment requestEnroll(Long sessionId, Long nsUserId) {
        return new SessionEnrollment(null, sessionId, nsUserId, UserEnrollmentStatus.REQUESTED);
    }
    public static SessionEnrollment notAvailableEnroll(Long sessionId, Long nsUserId) {
        return new SessionEnrollment(null, sessionId, nsUserId, UserEnrollmentStatus.NOT_AVAILABLE);
    }

    public void approve() {
        if (status != UserEnrollmentStatus.REQUESTED) {
            throw new IllegalStateException("Cannot approve enrollment that is not requested.");
        }
        this.status = UserEnrollmentStatus.ENROLLED;
    }

    public void reject() {
        if (status != UserEnrollmentStatus.REQUESTED) {
            throw new IllegalStateException("Cannot approve enrollment that is not requested.");
        }
        this.status = UserEnrollmentStatus.REJECTED;
    }

    public boolean isAvailable() {
        return status != UserEnrollmentStatus.NOT_AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public UserEnrollmentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // 같은 객체인지 확인
        if (o == null || getClass() != o.getClass()) return false;  // null이거나 클래스가 다른 경우
        SessionEnrollment that = (SessionEnrollment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(nsUserId, that.nsUserId) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, nsUserId, status);
    }
}
