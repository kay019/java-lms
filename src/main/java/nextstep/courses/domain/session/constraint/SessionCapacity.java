package nextstep.courses.domain.session.constraint;

import java.util.Objects;

public class SessionCapacity {
    private final int value;

    public SessionCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("수강 인원은 1 이상이여야 합니다.");
        }
        this.value = capacity;
    }

    public int value() {
        return value;
    }

    public boolean isGreaterThan(int value) {
        return value > this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionCapacity that = (SessionCapacity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
