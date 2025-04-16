package nextstep.sessions.domain.type;

import nextstep.payments.domain.Payment;

public class FreeSessionType implements SessionType {
    @Override
    public boolean isRegisterable(Payment payment, Long memberCount) {
        return true;
    }
}
