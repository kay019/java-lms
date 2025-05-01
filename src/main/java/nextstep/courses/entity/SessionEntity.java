package nextstep.courses.entity;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.infrastructure.SessionFeePolicy;

import java.time.LocalDateTime;

public class SessionEntity {
    private static final int FREE_FEE = 0;
    private static final int ZERO_STUDENT = 0;

    private final Long id;
    private final String sessionStatus;
    private final String enrollStatus;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final LocalDateTime createdAt;
    private final String feePolicy;
    private final int fee;
    private final int maxStudent;

    private SessionEntity(Long id, String sessionStatus, String enrollStatus,
                          LocalDateTime startedAt, LocalDateTime endedAt,
                          LocalDateTime createdAt, String feePolicy,
                          int fee, int maxStudent) {
        this.id = id;
        this.sessionStatus = sessionStatus;
        this.enrollStatus = enrollStatus;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.createdAt = createdAt;
        this.feePolicy = feePolicy;
        this.fee = fee;
        this.maxStudent = maxStudent;
    }

    public static SessionEntity toEntity(Session session) {
        if (session instanceof FreeSession) {
            return toFreeSessionEntity((FreeSession) session);
        }
        return toPaidSessionEntity((PaidSession) session);
    }

    private static SessionEntity toFreeSessionEntity(FreeSession session) {
        return new SessionEntity(
                session.getId(),
                session.getSessionStatus().name(),
                session.getEnrollStatus().name(),
                session.getDate().getStartedAt(),
                session.getDate().getEndedAt(),
                session.getCreatedAt(),
                SessionFeePolicy.FREE.name(),
                FREE_FEE,
                ZERO_STUDENT
        );
    }

    private static SessionEntity toPaidSessionEntity(PaidSession session) {
        return new SessionEntity(
                session.getId(),
                session.getSessionStatus().name(),
                session.getEnrollStatus().name(),
                session.getDate().getStartedAt(),
                session.getDate().getEndedAt(),
                session.getCreatedAt(),
                SessionFeePolicy.PAID.name(),
                session.getFee(),
                session.getMaxStudent()
        );
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public String getEnrollStatus() {
        return enrollStatus;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getFeePolicy() {
        return feePolicy;
    }

    public int getFee() {
        return fee;
    }

    public int getMaxStudent() {
        return maxStudent;
    }
}
