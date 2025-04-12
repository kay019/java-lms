package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.property.SessionProperty;

public class SessionDescriptor {

    private final SessionCoverImage image;

    private final SessionPeriod period;

    private final SessionProperty property;

    public SessionDescriptor(SessionCoverImage image, SessionPeriod period, SessionProperty property) {
        this.image = image;
        this.period = period;
        this.property = property;
    }

    public boolean canNotEnroll(SessionPayments sessionPayments, Payment payment) {
        return property.canNotEnroll(sessionPayments, payment);
    }
}
