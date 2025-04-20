package nextstep.session.domain;

import nextstep.enrollment.domain.Enrollment;
import nextstep.exception.FreeSessionIllegalArgumentException;

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
            throw new FreeSessionIllegalArgumentException();
        }

        if (!ENROLLING.equals(getStatus())) {
            throw new FreeSessionIllegalArgumentException();
        }

        if (enrollment.isNotValid(FREE)) {
            throw new FreeSessionIllegalArgumentException();
        }

        getStudents().add(enrollment.getStudent());
    }
}
