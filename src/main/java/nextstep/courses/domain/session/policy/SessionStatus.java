package nextstep.courses.domain.session.policy;

import java.util.stream.Stream;

public enum SessionStatus {
    PREPARING, ENROLLING, CLOSED;

    public boolean canEnroll() {
        return this == ENROLLING;
    }

    public static SessionStatus fromString(String sessionStatus) {
        return Stream.of(SessionStatus.values())
            .filter(status -> status.name().equalsIgnoreCase(sessionStatus))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown session status: " + sessionStatus));
    }
}
