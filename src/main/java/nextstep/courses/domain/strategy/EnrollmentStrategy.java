package nextstep.courses.domain.strategy;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;

public interface EnrollmentStrategy {
    void validateEnrollment(Session session, Payment payment);
}
