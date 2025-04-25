package nextstep.courses.domain;

public enum ApplicationState {
    PENDING,
    APPROVED,
    REJECTED;

    public boolean isPending() {
        return this == PENDING;
    }
}
