package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidRegsiterStrategy implements RegisterStrategy {
    private final NaturalNumber price;
    private final NaturalNumber capacity;

    public PaidRegsiterStrategy(Long price, Long capacity) {
        this.price = new NaturalNumber(price);
        this.capacity = new NaturalNumber(capacity);
    }

    @Override
    public Payment register(NsUser user, Long sessionId, int studentCount, NaturalNumber money) {
        validateMoney(money);
        validateStudentCapacity(studentCount);
        return new Payment("", sessionId, user.getId(), money.value());
    }

    private void validateStudentCapacity(int studentCount) {
        if (capacity.compareTo(studentCount) > 0) {
            throw new CannotRegisterException("해당 강의의 수강 정원이 모두 마감되었습니다.");
        }
    }

    private void validateMoney(NaturalNumber money) {
        if (!money.equals(this.price)) {
            throw new CannotRegisterException("결제 금액이 강의의 가격과 같지 않습니다.");
        }
    }
}
