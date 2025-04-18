package nextstep.payments.domain;

import nextstep.payments.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository {
    long save(PaymentEntity paymentEntity);

    List<PaymentEntity> findBySession(Long sessionId);
}
