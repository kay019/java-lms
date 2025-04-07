package nextstep.courses.domain;

public class SessionBuilder {
    private Long id = 1L;
    private Course course;
    private CoverImage coverImage = CoverImageTest.createCoverImage1();
    private SessionStatus sessionStatus;
    private RegistrationPolicy registrationPolicy;
    private SessionPeriod sessionPeriod = SessionPeriodTest.createSessionPeriod1();

    public SessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessionBuilder course(Course course) {
        this.course = course;
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

    public SessionBuilder paid(int sessionFee, int maxStudentCount) {
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
        Session session = new Session(
                id,
                coverImage,
                sessionStatus,
                registrationPolicy,
                sessionPeriod
        );

        if (course != null) {
            session.toCourse(course);
        }

        return session;
    }
}