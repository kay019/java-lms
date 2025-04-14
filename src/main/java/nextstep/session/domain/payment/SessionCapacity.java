package nextstep.session.domain.payment;

public class SessionCapacity {
    private final int value;

    public SessionCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("수강 인원은 1 이상이여야 합니다.");
        }
        this.value = capacity;
    }

    public boolean isGreaterThan(int value) {
        return value > this.value;
    }
}
