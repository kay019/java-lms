package nextstep.courses.domain;

import nextstep.exception.MoneyIllegalArgumentException;

public class Money {
    private final long amount;
    private final String currency;

    public Money(long amount) {
        this(amount, "KRW");
    }

    public Money(long amount, String currency) {
        validate(amount);

        this.amount = amount;
        this.currency = currency;
    }

    private void validate(long amount) {
        if (amount < 0) {
            throw new MoneyIllegalArgumentException();
        }
    }

    public boolean isValid(long amount) {
        return this.amount == amount;
    }

    public String getCurrency() {
        return currency;
    }
}
