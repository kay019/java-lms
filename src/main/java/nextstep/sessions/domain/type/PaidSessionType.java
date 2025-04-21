package nextstep.sessions.domain.type;

import nextstep.payments.domain.Payment;

public class PaidSessionType extends SessionType {
    public PaidSessionType(Long maxMember, Long price) {
        super(maxMember, price);
    }

    @Override
    public void register(Payment payment, Long currentMemberCount) {
        validatePayment(payment);
        validateMemberLimit(currentMemberCount);
    }

    @Override
    protected void validatePayment(Payment payment) {
        if (!payment.isSameAmountAs(price)) {
            throw new IllegalArgumentException("payment price is not same as session price");
        }
    }

    @Override
    protected void validateMemberLimit(Long currentMemberCount) {
        if (currentMemberCount > maxMemberCount) {
            throw new IllegalArgumentException("member count is over limit");
        }
    }


}
