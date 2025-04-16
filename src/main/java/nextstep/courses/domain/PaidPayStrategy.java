package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidPayStrategy implements PayStrategy {
    private final PositiveNumber price;

    public PaidPayStrategy(Long price) {
        this.price = new PositiveNumber(price);
    }

    @Override
    public void pay(NsUser user, Long sessionId, PositiveNumber money) {
        validateMoney(money);

        Payment payment = new Payment("", sessionId, user.getId(), money.value());
        if (!payment.isPaid()) {
            throw new CannotRegisterException("결제에 문제가 발생했습니다.");
        }
    }

    private void validateMoney(PositiveNumber money) {
        if (!money.equals(this.price)) {
            throw new CannotRegisterException("결제 금액이 강의의 가격과 같지 않습니다.");
        }
    }
}
