package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;

public interface EnrollStrategy {
    void validate(Enrollments enrollments, Payment payment, int maxParticipants, Long fee);
}
