package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payments;

public class SessionBuilder {
    private Long id = 1L;
    private long courseId = 1L;
    private CoverImage coverImage = CoverImageTest.createCoverImage1();
    private SessionStatus sessionStatus;
    private RegistrationPolicy registrationPolicy;
    private SessionPeriod sessionPeriod = SessionPeriodTest.createSessionPeriod1();
    private Payments payments = new Payments();

    public SessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessionBuilder courseId(long courseId) {
        this.courseId = courseId;
        return this;
    }

    public SessionBuilder coverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public SessionBuilder sessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
        return this;
    }

    public SessionBuilder paid(long sessionFee, int maxStudentCount) {
        this.registrationPolicy = new PaidRegistrationPolicy(sessionFee, maxStudentCount);
        return this;
    }

    public SessionBuilder free() {
        this.registrationPolicy = new FreeRegistrationPolicy();
        return this;
    }

    public SessionBuilder sessionPeriod(SessionPeriod sessionPeriod) {
        this.sessionPeriod = sessionPeriod;
        return this;
    }

    public Session build() {
        return new Session(
                id,
                courseId,
                coverImage,
                sessionStatus,
                registrationPolicy,
                sessionPeriod);
    }
}
