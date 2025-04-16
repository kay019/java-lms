package nextstep.sessions.domain.type;

import java.util.Objects;
import nextstep.payments.domain.Payment;

public class PaidSessionType implements SessionType {
    private final Long maxMember;

    private final Long amount;

    public PaidSessionType(Long maxMember, Long amount) {
        this.maxMember = maxMember;
        this.amount = amount;
    }

    @Override
    public boolean isRegisterable(Payment payment, Long memberCount) {
        validatePayment(payment);
        validateMemberLimit(memberCount);
        return true;
    }

    private void validatePayment(Payment payment) {
        if (!payment.isSameAmountAs(amount)) {
            throw new IllegalArgumentException("payment amount is not same as session amount");
        }
    }

    private void validateMemberLimit(Long memberCount) {
        if (memberCount > maxMember) {
            throw new IllegalArgumentException("member count is over limit");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaidSessionType that = (PaidSessionType) o;
        return Objects.equals(maxMember, that.maxMember) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxMember, amount);
    }
}
