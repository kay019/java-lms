package nextstep.courses.domain;

public enum SessionState {
    PREPARING,
    RECRUTING,
    FINISHED;

    public boolean canNotRegister() {
        return this != RECRUTING;
    }
}
