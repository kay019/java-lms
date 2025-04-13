package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidEnrollmentPolicy implements EnrollmentPolicy {
  private final int capacity;
  private final long price;

  public PaidEnrollmentPolicy(int capacity, long price) {
    this.capacity = capacity;
    this.price = price;
  }

  @Override
  public MoneyType moneyType() {
    return MoneyType.PAID;
  }

  @Override
  public void checkEnrollAvailability(Session session, Payment payment) {
    if (session.enrolledCount() >= capacity) {
      throw new IllegalStateException("정원이 초과되었습니다.");
    }
    if (payment == null || !payment.isSameAmount(this.price)) {
      throw new IllegalStateException("결제 정보가 유효하지 않습니다.");
    }
  }
}
