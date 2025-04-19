package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

public class SessionDescriptor {

    private final SessionImage image;

    private final SessionPeriod period;

    private final SessionEnrollPolicy policy;

    public SessionDescriptor(SessionImage image, SessionPeriod period, SessionEnrollPolicy policy) {
        this.image = image;
        this.period = period;
        this.policy = policy;
    }

    public String imageUrl() {
        return image.url();
    }

    public String imageType() {
        return image.type();
    }

    public LocalDateTime startDate() {
        return period.startDate();
    }

    public LocalDateTime endDate() {
        return period.endDate();
    }

    public String status() {
        return policy.status();
    }

    public String type() {
        return policy.type();
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollCount, long amount) {
        return policy.canEnroll(sessionConstraint, enrollCount, amount);
    }

    public BufferedImage image() throws IOException {
        return image.image();
    }
}
