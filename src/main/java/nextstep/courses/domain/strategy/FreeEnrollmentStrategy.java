package nextstep.courses.domain.strategy;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;

public class FreeEnrollmentStrategy implements EnrollmentStrategy {
    @Override
    public void validateEnrollment(Session session, Payment payment) {
    }
}
