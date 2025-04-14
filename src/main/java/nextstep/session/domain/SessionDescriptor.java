package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.payment.SessionConstraint;
import nextstep.session.domain.property.SessionProperty;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SessionDescriptor {

    private final SessionImage image;

    private final SessionPeriod period;

    private final SessionProperty property;

    public SessionDescriptor(SessionImage image, SessionPeriod period, SessionProperty property) {
        this.image = image;
        this.period = period;
        this.property = property;
    }

    public boolean canNotEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment) {
        return property.canNotEnroll(sessionConstraint, payments, payment);
    }

    public BufferedImage image() {
        return image.image();
    }

    public void updateImage() throws IOException {
        image.updateImage();
    }
}
