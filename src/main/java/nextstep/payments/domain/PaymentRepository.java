package nextstep.payments.domain;

import nextstep.payments.record.PaymentRecord;

import java.util.List;

public interface PaymentRepository {
    int save(PaymentRecord paymentRecord);
    PaymentRecord findById(long id);
    List<PaymentRecord> findBySessionId(long sessionId);
}
