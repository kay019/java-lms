package nextstep.payments.domain;

import nextstep.payments.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository {
    long save(Payment payment);

    List<PaymentEntity> findBySession(Long sessionId);
}
