package nextstep.payments.domain;

import nextstep.courses.domain.Course;

import java.util.List;

public interface PaymentRepository {
    int save(Payment payment);

    List<Payment> findBySession(Long sessionId);
}
