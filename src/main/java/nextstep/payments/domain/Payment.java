package nextstep.payments.domain;

import java.time.LocalDateTime;

import nextstep.courses.domain.Money;
import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public class Payment {
    private Long id;
    private final long sessionId;
    private final NsUser nsUser;
    private final Money amount;
    private final LocalDateTime createdAt;

    public Payment(long sessionId, NsUser nsUser, Money amount) {
        this(null, sessionId, nsUser, amount, LocalDateTime.now());
    }

    public Payment(Long id, long sessionId, NsUser nsUser, Money amount, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUser = nsUser;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public long getSessionId() {
        return sessionId;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
