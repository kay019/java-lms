package nextstep.payments.domain;

import java.time.LocalDateTime;

import nextstep.Identifiable;
import nextstep.session.domain.Money;
import nextstep.users.domain.NsUser;

public class Payment implements Identifiable {
    private Long id;
    private final long sessionId;
    private final long nsUserId;
    private final Money amount;
    private final LocalDateTime createdAt;

    public Payment(long sessionId, long nsUserId, Money amount) {
        this(null, sessionId, nsUserId, amount, LocalDateTime.now());
    }

    public Payment(Long id, long sessionId, long nsUserId, Money amount, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getNsUserId() {
        return nsUserId;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean isUnsaved() {
        return getId() == null;
    }
}
