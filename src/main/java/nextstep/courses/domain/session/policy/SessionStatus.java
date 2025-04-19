package nextstep.courses.domain.session.policy;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SessionStatus {
    PREPARING("preparing"),
    ENROLLING("enrolling"),
    CLOSED("closed");

    private final String status;

    SessionStatus(String status) {
        this.status = status;
    }

    public boolean canEnroll() {
        return this == ENROLLING;
    }

    public static SessionStatus fromString(String status) {
        return Arrays.stream(values())
            .filter(value -> value.status.equalsIgnoreCase(status))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("'%s'은(는) 유효한 세션 상태가 아닙니다.", status)));
    }
}
