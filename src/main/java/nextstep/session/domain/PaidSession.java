package nextstep.session.domain;

import nextstep.enrollment.domain.Enrollment;
import nextstep.session.exception.PaidSessionIllegalArgumentException;

import static nextstep.session.domain.SessionStatus.ENROLLING;

public class PaidSession extends Session {
    private final int maxCapacity;
    private final int fee;

    public static class Builder extends Session.Builder<Builder> {
        private int maxCapacity;
        private int fee;

        public Builder maxCapacity(int maxCapacity) {
            this.maxCapacity = maxCapacity;
            return this;
        }

        public Builder fee(int fee) {
            this.fee = fee;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public PaidSession build() {
            return new PaidSession(this);
        }
    }

    private PaidSession(Builder builder) {
        super(builder);
        this.maxCapacity = builder.maxCapacity;
        this.fee = builder.fee;
    }

    @Override
    public void enroll(Enrollment enrollment) {
        validateEnroll(enrollment);
        getStudents().add(enrollment.getStudent());
    }

    private void validateEnroll(Enrollment enrollment) {
        if (enrollment == null) {
            throw new PaidSessionIllegalArgumentException();
        }

        if (!ENROLLING.equals(getStatus())) {
            throw new PaidSessionIllegalArgumentException();
        }

        if (enrollment.isNotValid(fee)) {
            throw new PaidSessionIllegalArgumentException();
        }

        if (isDuplicateStudent(enrollment)) {
            throw new PaidSessionIllegalArgumentException();
        }

        if (getStudents().size() >= maxCapacity) {
            throw new PaidSessionIllegalArgumentException();
        }
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getFee() {
        return fee;
    }
}
