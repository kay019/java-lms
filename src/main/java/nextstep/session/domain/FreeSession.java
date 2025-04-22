package nextstep.session.domain;

import nextstep.enrollment.domain.Enrollment;
import nextstep.session.exception.FreeSessionDuplicateStudentException;
import nextstep.session.exception.FreeSessionEnrollmentRequiredException;
import nextstep.session.exception.FreeSessionInvalidEnrollmentException;
import nextstep.session.exception.FreeSessionNotEnrollingException;

import static nextstep.session.domain.SessionStatus.ENROLLING;

public class FreeSession extends Session {
    public static final int FREE = 0;

    public static class Builder extends Session.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public FreeSession build() {
            return new FreeSession(this);
        }
    }

    private FreeSession(Builder builder) {
        super(builder);
    }

    @Override
    public void enroll(Enrollment enrollment) {
        if (enrollment == null) {
            throw new FreeSessionEnrollmentRequiredException();
        }

        if (!ENROLLING.equals(getStatus())) {
            throw new FreeSessionNotEnrollingException();
        }

        if (isDuplicateStudent(enrollment)) {
            throw new FreeSessionDuplicateStudentException();
        }

        if (enrollment.isNotValid(FREE)) {
            throw new FreeSessionInvalidEnrollmentException();
        }

        getStudents().add(enrollment.getStudent());
    }
}
