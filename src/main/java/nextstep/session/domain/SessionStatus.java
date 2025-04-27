package nextstep.session.domain;

import java.util.Arrays;

public enum SessionStatus {
    READY("준비중"),
    ONGOING("모집중"),
    CLOSED("종료");

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

    public static SessionStatus from(String description) {
        return Arrays.stream(values())
                .filter(status -> status.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for description: " + description));
    }

    public boolean isSameAs(SessionStatus sessionStatus) {
        return this == sessionStatus;
    }

    public String getDescription() {
        return description;
    }
}
