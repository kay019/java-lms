package nextstep.courses.domain;

import java.util.Objects;

public class NaturalNumber implements Comparable<NaturalNumber> {

    private final int value;

    public NaturalNumber(int value) {
        validate(value);
        this.value = value;
    }

    private static void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("0을 포함한 자연수만 허용 가능합니다.");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NaturalNumber that = (NaturalNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return getValue() + "";
    }

    @Override
    public int compareTo(NaturalNumber o) {
        return Integer.compare(getValue(), o.getValue());
    }

    public int compareTo(int o) {
        return compareTo(new NaturalNumber(o));
    }
}
