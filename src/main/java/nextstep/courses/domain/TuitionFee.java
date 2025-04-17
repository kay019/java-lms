package nextstep.courses.domain;

public class TuitionFee {
    private final int amount;

    public TuitionFee(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("수강료는 0 이거나 음수일 수 없습니다.");
        }
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}