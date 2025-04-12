package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.property.SessionProperty;

public class SessionDescriptor {

    private final SessionImage image;

    private final SessionPeriod period;

    private final SessionProperty property;

    public SessionDescriptor(SessionImage image, SessionPeriod period, SessionProperty property) {
        this.image = image;
        this.period = period;
        this.property = property;
    }

    public boolean canNotEnroll(SessionPayments sessionPayments, Payment payment) {
        return property.canNotEnroll(sessionPayments, payment);
    }
}
