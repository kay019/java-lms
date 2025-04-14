package nextstep.courses.domain;

public enum SessionState {
    PREPARING,
    RECRUTING,
    FINISHED;

    public static boolean canNotRegister(SessionState sessionState) {
        return sessionState != RECRUTING;
    }
}
