package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payments;

public class SessionBuilder {
    private Long id = 1L;
    private long courseId = 1L;
    private CoverImages coverImages = new CoverImages();
    private SessionProgressStatus progressStatus = SessionProgressStatus.ONGOING;
    private RecruitmentStatus recruitmentStatus = RecruitmentStatus.RECRUITING;
    private RegistrationPolicy registrationPolicy;
    private SessionPeriod sessionPeriod = SessionPeriodTest.createSessionPeriod1();
    private boolean selectionRequired = false;

    public SessionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessionBuilder courseId(long courseId) {
        this.courseId = courseId;
        return this;
    }

    public SessionBuilder progressStatus(SessionProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
        return this;
    }

    public SessionBuilder recruitmentStatus(RecruitmentStatus recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
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

    public SessionBuilder selectionRequired(boolean selectionRequired) {
        this.selectionRequired = selectionRequired;
        return this;
    }

    public SessionBuilder sessionPeriod(SessionPeriod sessionPeriod) {
        this.sessionPeriod = sessionPeriod;
        return this;
    }

    public Session build() {
        Session session = new Session(
                id,
                courseId,
                progressStatus,
                recruitmentStatus,
                registrationPolicy,
                sessionPeriod,
                selectionRequired);

        session.addCoverImages(coverImages);

        return session;
    }
}
