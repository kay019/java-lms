package nextstep.session.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.payment.SessionPayments;
import nextstep.session.domain.payment.SessionType;

import java.time.LocalDateTime;

public class Session extends BaseDomain {

    private Course course;

    private final SessionCoverImage image;

    private final SessionPeriod period;

    private final SessionPayments payments;

    private final SessionStatus status;

    private final SessionType type;

    public Session(Long id, Course course, SessionCoverImage image, SessionPayments payments, SessionPeriod period) {
        super(id);
        this.course = course;
        this.image = image;
        this.period = period;
        this.payments = payments;
        this.status = SessionStatus.PREPARING;
        this.type = SessionType.FREE;
    }

    public Session(Long id, Course course, SessionCoverImage image, SessionPayments payments, SessionPeriod period, SessionStatus status, SessionType type) {
        super(id);
        this.course = course;
        this.image = image;
        this.period = period;
        this.payments = payments;
        this.status = status;
        this.type = type;
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
