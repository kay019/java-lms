package nextstep.courses.domain;

import nextstep.courses.InvalidNaturalNumberException;

import java.util.Objects;

public class PositiveNumber implements Comparable<PositiveNumber> {
    private final Long value;
    public PositiveNumber(Long value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(Long value) {
        if (value < 0) {
            throw new InvalidNaturalNumberException("0보다 큰 수 여야 합니다.");
        }
    }

    public Long value() {
        return value;
    }

    @Override
    public int compareTo(PositiveNumber number) {
        return (int)(number.value - this.value);
    }

    public int compareTo(int number) {
        return (int)(number- this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PositiveNumber that = (PositiveNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
