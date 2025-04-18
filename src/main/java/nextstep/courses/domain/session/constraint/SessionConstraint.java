package nextstep.courses.domain.session.constraint;

public class SessionConstraint {

    private final SessionFee fee;

    private final SessionCapacity capacity;

    public SessionConstraint(long fee, int capacity) {
        this(new SessionFee(fee), new SessionCapacity(capacity));
    }

    public SessionConstraint(SessionFee fee, SessionCapacity capacity) {
        this.fee = fee;
        this.capacity = capacity;
    }

    public long fee() {
        return fee.value();
    }

    public int capacity() {
        return capacity.value();
    }

    public boolean isSameFee(long amount) {
        return fee.isSame(amount);
    }

    public boolean isGreaterThenCapacity(int value) {
        return capacity.isGreaterThan(value);
    }
}
