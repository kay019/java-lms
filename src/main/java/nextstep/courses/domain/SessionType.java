package nextstep.courses.domain;

public enum SessionType {
    PAID, FREE;

    public boolean isPaid() {
        return this == PAID;
    }

    public boolean isFree() {
        return this == FREE;
    }
}

