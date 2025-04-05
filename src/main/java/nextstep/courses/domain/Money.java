package nextstep.courses.domain;

import java.util.Objects;

public class Money {
    private final NaturalNumber value;

    public Money(int amount) {
        this.value = new NaturalNumber(amount);
    }

    public int getAmount() {
        return value.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
