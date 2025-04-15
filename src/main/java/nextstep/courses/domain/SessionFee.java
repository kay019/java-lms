package nextstep.courses.domain;

public class SessionFee {
    private int value;

    public SessionFee(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("fee must be positive");
        }

        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isEqual(Long amount) {
        if (amount == null) {
            return false;
        }
        return this.value == amount;
    }
}
