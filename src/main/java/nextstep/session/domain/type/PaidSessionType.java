package nextstep.session.domain.type;

import java.util.Objects;

public class PaidSessionType extends SessionType {
    public PaidSessionType(Long price) {
        super(price);
    }

    @Override
    protected void validatePayment(Long amount) {
        if (!Objects.equals(this.price, amount)) {
            throw new IllegalArgumentException("payment price is not same as session price");
        }
    }

}
