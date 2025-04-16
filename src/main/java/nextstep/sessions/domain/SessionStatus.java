package nextstep.sessions.domain;

public enum SessionStatus {
    READY("준비중"),
    ONGOING("모집중"),
    CLOSED("종료");

    private final String desc;

    SessionStatus(String desc) {
        this.desc = desc;
    }
}
