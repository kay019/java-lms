package nextstep.payments.domain;

import java.util.Optional;
import java.util.List;

public interface PaymentRepository {

    List<Payment> findAllBySessionIdAndUserId(Long sessionId, Long userId);
} 
