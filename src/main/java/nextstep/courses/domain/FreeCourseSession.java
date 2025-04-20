package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeCourseSession extends CourseSession {

    public FreeCourseSession(Long courseId, SessionCoverImage image, SessionStatus status, String title, SessionPeriod period) {
        super(courseId, image, SessionType.FREE, status, title, period);
    }

    @Override
    void checkPossibleToRegister(Long nsUserId, Payment payment) {}
}
