package nextstep.sessionstudent;

import java.time.LocalDateTime;

import nextstep.Identifiable;
import nextstep.courses.domain.Course;
import nextstep.session.domain.Session;

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
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void changeStatus(SessionStudentStatus status, ApprovalContext context) {
        if (!isPending()) {
            throw new IllegalStateException("세션 수강신청이 대기 상태가 아닙니다.");
        }

        if (!context.isSessionSelectionRequired()) {
            throw new IllegalStateException("선발 가능한 세션이 아닙니다.");
        }

        if (!context.isCourseOwner()) {
            throw new IllegalStateException("세션 수강신청을 승인/거절할 권한이 없습니다.");
        }

        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void approved(ApprovalContext context) {
        changeStatus(SessionStudentStatus.APPROVED, context);
    }

    public void cancelled(ApprovalContext context) {
        changeStatus(SessionStudentStatus.CANCELLED, context);
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
