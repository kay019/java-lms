package nextstep.courses.domain;

public class Capacity {
    private final int value;

    public Capacity(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("수강 정원은 0이거나 음수일 수 없습니다.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isFull(int registeredCount) {
        return registeredCount >= value;
    }
}