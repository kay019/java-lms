package nextstep.courses.session.domain;

public enum EnrollStatus {
    NON_RECRUIT,
    RECRUIT;

    public boolean canNotEnroll() {
        return this == NON_RECRUIT;
    }
}
