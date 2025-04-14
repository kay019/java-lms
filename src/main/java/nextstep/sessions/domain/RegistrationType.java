package nextstep.sessions.domain;

public enum RegistrationType {
    FREE,
    PAID;

    public boolean isFree() {
        return this == FREE;
    }
}
