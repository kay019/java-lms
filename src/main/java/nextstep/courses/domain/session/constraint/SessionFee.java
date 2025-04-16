package nextstep.courses.domain.session.constraint;

public class SessionFee {
    private final long value;

    public SessionFee(long fee) {
        if (fee < 1) {
            throw new IllegalArgumentException("강의 요금은 0원 이상이여야 합니다.");
        }
        this.value = fee;
    }

    public boolean isSame(long value) {
        return this.value == value;
    }
}
