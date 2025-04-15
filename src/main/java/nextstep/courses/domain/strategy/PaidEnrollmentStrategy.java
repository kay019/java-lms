package nextstep.courses.domain.strategy;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;

public class PaidEnrollmentStrategy implements EnrollmentStrategy {
    private int enrollmentLimitCount = Integer.MIN_VALUE;

    public PaidEnrollmentStrategy(int enrollmentLimitCount) {
        this.enrollmentLimitCount = enrollmentLimitCount;
    }

    @Override
    public void validateEnrollment(Session session, Payment payment) {
        if (session.isExceedLimitEnrollmentCount(this.enrollmentLimitCount)) {
            throw new IllegalStateException("Enrollment limit exceeded.");
        }

        if(!session.isSameFee(payment)){
            throw new IllegalStateException("Enrollment amount mismatch.");
        }
    }
}
