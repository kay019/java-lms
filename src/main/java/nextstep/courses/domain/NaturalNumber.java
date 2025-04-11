package nextstep.courses.domain;

import java.util.Objects;

public class NaturalNumber implements Comparable<NaturalNumber> {
    private final Long value;
    public NaturalNumber(Long value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(Long value) {
        if (value <= 0) {
            throw new InvalidNaturalNumberException("0보다 큰 수 여야 합니다.");
        }
    }

    public Long value() {
        return value;
    }

    @Override
    public int compareTo(NaturalNumber number) {
        return (int)(number.value - this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NaturalNumber that = (NaturalNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
