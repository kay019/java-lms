package nextstep.session.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.property.SessionProperty;
import nextstep.session.domain.property.SessionStatus;
import nextstep.session.domain.property.SessionType;

import java.time.LocalDateTime;

public class Session extends BaseDomain {

    private Course course;

    private final SessionCoverImage image;

    private final SessionPeriod period;

    private final SessionPayments payments;

    private final SessionProperty property;

    public Session(Long id, Course course, SessionCoverImage image, SessionPayments payments, SessionPeriod period) {
        super(id);
        this.course = course;
        this.image = image;
        this.period = period;
        this.payments = payments;
        this.property = new SessionProperty();
    }

    public Session(Long id, Course course, SessionCoverImage image, SessionPayments payments, SessionPeriod period, SessionStatus status, SessionType type) {
        super(id);
        this.course = course;
        this.image = image;
        this.period = period;
        this.payments = payments;
        this.property = new SessionProperty(status, type);
    }

    public void toCourse(Course course) {
        this.course = course;
    }

    public boolean addPayment(Payment payment) {
        if (property.canNotEnroll(payments, payment)) {
            return false;
        }
        payments.add(payment);
        payment.toSession(this);
        this.updatedAt = LocalDateTime.now();
        return true;
    }
}
