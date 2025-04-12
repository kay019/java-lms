package nextstep.session.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.payment.SessionPayments;

import java.time.LocalDateTime;

public class Session extends BaseDomain {

    private Course course;

    private final SessionCoverImage image;

    private final SessionPeriod period;

    private final SessionPayments payments;

    public Session(Long id, Course course, SessionCoverImage image, SessionPayments payments, SessionPeriod period) {
        super(id);
        this.course = course;
        this.image = image;
        this.period = period;
        this.payments = payments;
    }

    public void toCourse(Course course) {
        this.course = course;
    }

    public boolean addPayment(Payment payment) {
        if (payments.add(payment)) {
            payment.toSession(this);
            this.updatedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }
}
