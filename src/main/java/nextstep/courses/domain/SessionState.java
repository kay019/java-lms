package nextstep.courses.domain;

public enum SessionState {
    READY, START, END;


    public boolean isRequestSession() {
        return this == START;
    }
}
