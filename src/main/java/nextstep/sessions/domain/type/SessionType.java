package nextstep.sessions.domain.type;

import java.util.Objects;
import nextstep.payments.domain.Payment;

public abstract class SessionType {

    protected final Long maxMemberCount;
    protected final Long price;

    protected SessionType(Long maxMemberCount, Long price) {
        this.maxMemberCount = maxMemberCount;
        this.price = price;
    }

    public void register(Payment payment, Long currentMemberCount) {
        validateMemberLimit(currentMemberCount);
        validatePayment(payment);
    }

    protected abstract void validateMemberLimit(Long currentMemberCount);

    protected abstract void validatePayment(Payment payment);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionType that = (SessionType) o;
        return Objects.equals(maxMemberCount, that.maxMemberCount) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxMemberCount, price);
    }
}
