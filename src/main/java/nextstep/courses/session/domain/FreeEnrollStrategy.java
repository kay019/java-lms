package nextstep.courses.session.domain;

import nextstep.courses.enrollment.domain.Enrollments;
import nextstep.payments.domain.Payment;

public class FreeEnrollStrategy implements EnrollStrategy{

    @Override
    public void validate(Enrollments enrollments, Payment payment, int maxParticipants, Long fee) {
    }
}
