package nextstep.session.domain;

import nextstep.session.domain.constraint.SessionConstraint;
import nextstep.session.domain.image.SessionImage;
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

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollCount, long amount) {
        return property.canEnroll(sessionConstraint, enrollCount, amount);
    }

    public BufferedImage image() {
        return image.image();
    }

    public void updateImage() throws IOException {
        image.updateImage();
    }
}
