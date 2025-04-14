package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.payment.SessionConstraint;

public class SessionProperty {

    private final SessionStatus status;

    private final SessionType type;

    public SessionProperty() {
        this(SessionStatus.PREPARING, SessionType.FREE);
    }

    public SessionProperty(SessionStatus status, SessionType type) {
        this.status = status;
        this.type = type;
    }

    public boolean canNotEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment) {
        return type.canNotEnroll(sessionConstraint, payments, payment) && status.canNotEnroll();
    }
}
