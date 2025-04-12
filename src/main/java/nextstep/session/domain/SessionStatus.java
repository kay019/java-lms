package nextstep.session.domain;

public enum SessionStatus {
    PREPARING,
    RECRUITING,
    ENDED;

    public boolean isRegistrable() {
        return this == RECRUITING;
    }
}
