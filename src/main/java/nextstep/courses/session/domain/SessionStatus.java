package nextstep.courses.session.domain;

public enum SessionStatus {
    PREPARING,
    OPEN,
    CLOSE;

    public boolean canNotEnroll() {
        return this == CLOSE;
    }
}
