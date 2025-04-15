package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING(),
    OPENED(),
    CLOSED();

    public boolean isAvailableEnrollment() {
        return this == PREPARING;
    }
}
