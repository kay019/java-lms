package nextstep.session.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.Course;
import nextstep.session.domain.image.SessionCoverImage;
import nextstep.session.domain.payment.SessionPayments;

import java.time.LocalDateTime;

public class Session extends BaseDomain {

    private Course course;

    private SessionCoverImage image;

    private SessionPeriod period;

    private SessionPayments payments;

    public Session(Long id, Course course, SessionCoverImage image, SessionPayments payments, SessionPeriod period) {
        super(id, LocalDateTime.now(), LocalDateTime.now());
        this.course = course;
        this.image = image;
        this.period = period;
        this.payments = payments;
    }

    public void toCourse(Course course) {
        this.course = course;
    }

}
