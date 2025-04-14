package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidPayStrategy implements PayStrategy {
    private final NaturalNumber price;
    private final NaturalNumber capacity;

    public PaidPayStrategy(Long price, Long capacity) {
        this.price = new NaturalNumber(price);
        this.capacity = new NaturalNumber(capacity);
    }

    @Override
    public void pay(NsUser user, Long sessionId, int studentCount, NaturalNumber money) {
        validateMoney(money);
        validateStudentCapacity(studentCount);

        Payment payment = new Payment("", sessionId, user.getId(), money.value());
        if (!payment.isPaid()) {
            throw new CannotRegisterException("결제에 문제가 발생했습니다.");
        }
    }

    private void validateStudentCapacity(int studentCount) {
        if (capacity.compareTo(studentCount) >= 0) {
            throw new CannotRegisterException("해당 강의의 수강 정원이 모두 마감되었습니다.");
        }
    }

    private void validateMoney(NaturalNumber money) {
        if (!money.equals(this.price)) {
            throw new CannotRegisterException("결제 금액이 강의의 가격과 같지 않습니다.");
        }
    }
}
