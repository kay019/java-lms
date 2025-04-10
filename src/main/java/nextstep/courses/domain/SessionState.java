package nextstep.courses.domain;

public enum SessionState {
    PREPARING,
    RECRUTING,
    FINISHED;

    public static boolean canRegister(SessionState sessionState) {
        return sessionState == RECRUTING;
    }
}
