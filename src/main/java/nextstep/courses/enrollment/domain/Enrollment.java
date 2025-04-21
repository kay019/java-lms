package nextstep.courses.enrollment.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Enrollment {
    private final Long id;
    private final Long sessionId;
    private final NsUser nsUser;
    private final Payment payment;
    private final LocalDateTime createdAt;

    public Enrollment(Long sessionId, NsUser nsUser, Payment payment) {
        this(null, sessionId, nsUser, payment, LocalDateTime.now());
    }

    public Enrollment(Long id, Long sessionId, NsUser nsUser, Payment payment, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUser = nsUser;
        this.payment = payment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
