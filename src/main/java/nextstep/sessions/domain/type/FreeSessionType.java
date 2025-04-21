package nextstep.sessions.domain.type;

import nextstep.payments.domain.Payment;

public class FreeSessionType extends SessionType {
    private static final long DEFAULT_PARTICIPANT_COUNT = 0L;
    private static final long FREE_PRICE = 0L;

    public FreeSessionType() {
        super(DEFAULT_PARTICIPANT_COUNT, FREE_PRICE);
    }

    @Override
    protected void validateMemberLimit(Long currentMemberCount) {

    }

    @Override
    protected void validatePayment(Payment payment) {
    }
}
