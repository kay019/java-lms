package nextstep.session.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionImage;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.property.SessionProperty;
import nextstep.session.domain.property.SessionStatus;
import nextstep.session.domain.property.SessionType;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

public class Session extends BaseDomain {

    private Course course;

    private final SessionPayments payments;

    private final SessionDescriptor descriptor;

    public Session(Long id, Course course, SessionImage image, SessionPayments payments, SessionPeriod period) {
        super(id);
        this.course = course;
        this.descriptor = new SessionDescriptor(image, period, new SessionProperty());
        this.payments = payments;
    }

    public Session(Long id, Course course, SessionImage image, SessionPayments payments, SessionPeriod period, SessionStatus status, SessionType type) {
        super(id);
        this.course = course;
        this.descriptor = new SessionDescriptor(image, period, new SessionProperty(status, type));
        this.payments = payments;
    }

    public void link(Course course) {
        this.course = course;
    }

    public boolean addPayment(Payment payment) {
        if (descriptor.canNotEnroll(payments, payment)) {
            return false;
        }
        payments.add(payment);
        payment.link(this);
        this.updatedAt = LocalDateTime.now();
        return true;
    }

    public BufferedImage image() {
        return descriptor.image();
    }

    public void updateImage() throws IOException {
        descriptor.updateImage();
    }
}
