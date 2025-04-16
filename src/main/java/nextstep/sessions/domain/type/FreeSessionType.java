package nextstep.sessions.domain.type;

import nextstep.payments.domain.Payment;

public class FreeSessionType implements SessionType {
    @Override
    public void register(Payment payment, Long memberCount) {
        // empty
    }
}
