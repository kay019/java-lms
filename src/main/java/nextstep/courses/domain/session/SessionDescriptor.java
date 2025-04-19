package nextstep.courses.domain.session;

import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDescriptor that = (SessionDescriptor) o;
        return Objects.equals(image, that.image) && Objects.equals(period, that.period) && Objects.equals(policy, that.policy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, period, policy);
    }
}
