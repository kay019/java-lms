package nextstep.courses.domain;

import static nextstep.courses.domain.RequestStatus.REQUESTED;

public class Enrollment {
    private Long id;
    private Long sessionId;
    private Long userId;
    private RequestStatus status;

    public Enrollment(Long id, Long sessionId, Long userId, RequestStatus status) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.status = status;
    }

    public static Enrollment requestEnroll(Long sessionId, Long userId) {
        return new Enrollment(null, sessionId, userId, REQUESTED);
    }

    public void reject() {
        status.validateReject();
        this.status = RequestStatus.REJECTED;
    }

    public void approve() {
        status.validateApprove();
        this.status = RequestStatus.APPROVED;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }
}
