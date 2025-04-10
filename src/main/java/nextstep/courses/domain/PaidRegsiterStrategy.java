package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidRegsiterStrategy implements RegisterStrategy{
    private final Long price;
    private final int capacity;

    public PaidRegsiterStrategy(Long price, int capacity) {
        validatePrice(price);
        validateCapacity(capacity);
        this.price = price;
        this.capacity = capacity;
    }

    private void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new CannotCreateSessionException("최대 수용 인원수는 0보다 커야 합니다.");
        }
    }

    private void validatePrice(Long price) {
        if (price <= 0) {
            throw new CannotCreateSessionException("강의 가격은 0보다 커야 합니다.");
        }
    }

    @Override
    public Payment register(NsUser user, Long sessionId, Long money) {
        validateMoney(money);
        return new Payment("", sessionId, user.getId(), money);
    }

    private void validateMoney(Long money) {
        if (!money.equals(this.price)) {
            throw new CannotRegisterException("결제 금액이 강의의 가격과 같지 않습니다.");
        }
    }
}
