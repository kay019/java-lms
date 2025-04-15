package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.property.SessionEnrollPolicy;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SessionDescriptor {

    private final SessionImage image;

    private final SessionPeriod period;

    private final SessionEnrollPolicy property;

    public SessionDescriptor() {
        this(null, null, null);
    }

    public SessionDescriptor(SessionImage image, SessionPeriod period, SessionEnrollPolicy property) {
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
