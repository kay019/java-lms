package nextstep.session.domain;

import java.util.Arrays;

public enum SessionStatus {
    READY("준비중"),
    ONGOING("진행중"),
    CLOSED("종료");

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

    public static SessionStatus from(String name) {
        return SessionStatus.valueOf(name);
    }

    public boolean isSameAs(SessionStatus sessionStatus) {
        return this == sessionStatus;
    }

    public String getDescription() {
        return description;
    }
}
