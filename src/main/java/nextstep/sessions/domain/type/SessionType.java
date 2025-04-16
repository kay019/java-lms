package nextstep.sessions.domain.type;

import nextstep.payments.domain.Payment;

public interface SessionType {
    boolean isRegisterable(Payment payment, Long memberCount);
}
