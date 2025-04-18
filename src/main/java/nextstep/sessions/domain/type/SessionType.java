package nextstep.sessions.domain.type;

import nextstep.payments.domain.Payment;

public interface SessionType {
    void register(Payment payment, Long memberCount);
}
