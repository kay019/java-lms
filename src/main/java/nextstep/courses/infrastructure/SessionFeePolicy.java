package nextstep.courses.infrastructure;

public enum SessionFeePolicy {
    FREE("무료"),
    PAID("유료");

    private final String description;

    SessionFeePolicy(String description) {
        this.description = description;
    }
}
