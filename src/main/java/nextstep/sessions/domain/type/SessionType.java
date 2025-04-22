package nextstep.sessions.domain.type;

import java.util.Objects;

public abstract class SessionType {
    protected final Long price;

    protected SessionType(Long price) {
        this.price = price;
    }

    protected abstract void validatePayment(Long amount);

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionType that = (SessionType) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
