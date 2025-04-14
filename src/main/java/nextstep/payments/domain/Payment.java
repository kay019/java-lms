package nextstep.payments.domain;

import java.time.LocalDateTime;

public class Payment {
    private final String id;
    private final Long sessionId;
    private final Long nsUserId;
    private final Long amount;
    private final LocalDateTime createdAt;

    public Payment(String id, Long sessionId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Payment(Long sessionId, Long studentId, Long amount) {
        this(null, sessionId, studentId, amount);
    }

    public String getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
