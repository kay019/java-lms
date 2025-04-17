package nextstep.sessionstudent;

import java.time.LocalDateTime;

import nextstep.Identifiable;

public class SessionStudent implements Identifiable {

    private Long id;
    private final long sessionId;
    private final long nsUserId;
    private SessionStudentStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SessionStudent(long sessionId, long nsUserId, boolean selectionRequired) {
        this(null, sessionId, nsUserId, SessionStudentStatus.fromSelectRequired(selectionRequired), LocalDateTime.now(), LocalDateTime.now());
    }

    public SessionStudent(Long id, long sessionId, long nsUserId, SessionStudentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void changeStatus(SessionStudentStatus status) {
        if (!isPending()) {
            throw new IllegalStateException("세션 수강신청이 대기 상태가 아닙니다.");
        }

        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {
        changeStatus(SessionStudentStatus.APPROVED);
    }

    public void cancel() {
        changeStatus(SessionStudentStatus.CANCELLED);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getNsUserId() {
        return nsUserId;
    }

    public SessionStudentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean isUnsaved() {
        return getId() == null;
    }

    public boolean isApproved() {
        return status == SessionStudentStatus.APPROVED;
    }

    public boolean isPending() {
        return status == SessionStudentStatus.PENDING;
    }
}
