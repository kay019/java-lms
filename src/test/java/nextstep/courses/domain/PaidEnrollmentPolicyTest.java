package nextstep.courses.domain;


import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PaidEnrollmentPolicyTest {

  private final NsUser user = new NsUser(1L, "user1", "pw", "홍길동", "hong@test.com");
  private final PaidEnrollmentPolicy policy = new PaidEnrollmentPolicy(1, 10000L);
  private final Course course = new Course("백엔드 과정", 1L);
  private Session session = new Session(course, "test", null, null, policy);

  @Test
  void 정원이_남아있고_결제금액이_일치하면_통과() {
    Payment payment = new Payment(session, user, 10000L);
    policy.checkPayment(payment);
  }

  @Test
  void 정원이_초과되면_예외발생() {
    session.openForEnrollment();
    session.enroll(user, new Payment(session, user, 10000L));

    assertThatThrownBy(() -> policy.checkEnrollAvailability(session))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("정원이 초과되었습니다.");
  }

  @Test
  void 결제금액이_다르면_예외발생() {
    Payment wrongPayment = new Payment(session, user, 5000L);

    assertThatThrownBy(() -> policy.checkPayment(wrongPayment))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("결제 정보가 유효하지 않습니다.");
  }

  @Test
  void 결제정보가_null이면_예외발생() {
    assertThatThrownBy(() -> policy.checkPayment(null))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("결제 정보가 유효하지 않습니다.");
  }

}