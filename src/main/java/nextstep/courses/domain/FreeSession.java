package nextstep.courses.domain;

import nextstep.exception.FreeSessionIllegalArgumentException;
import nextstep.payments.domain.Payment;

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
    public void enroll(Student student, Payment payment) {
        if (student == null || payment == null) {
            throw new FreeSessionIllegalArgumentException();
        }

        if (payment.getAmount() != FREE) {
            throw new FreeSessionIllegalArgumentException();
        }

        getStudents().add(student);
    }
}
