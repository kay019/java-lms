package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.property.SessionProperty;

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
