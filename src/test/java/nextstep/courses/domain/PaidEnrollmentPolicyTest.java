package nextstep.courses.domain;


import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PaidEnrollmentPolicyTest {

  private PaidEnrollmentPolicy policy;
  private Session session;
  private NsUser user;
  private Course course;

  @BeforeEach
  void setUp() {
    course = new Course("백엔드 과정", 1L);
    policy = new PaidEnrollmentPolicy(2, 10000L);
    session = new Session(course, "test", null, null, policy);
    user = new NsUser(1L, "user1", "pw", "홍길동", "hong@test.com");
  }

  @Test
  void 정원이_남아있고_결제금액이_일치하면_통과() {
    Payment payment = new Payment("id", session, user, 10000L);
    policy.checkEnrollAvailability(session, payment);
  }

  @Test
  void 정원이_초과되면_예외발생() {
    session = new Session(1L, course, "test", LocalDateTime.now(), LocalDateTime.now(), SessionStatus.RECRUITING, new PaidEnrollmentPolicy(2, 10000L), new ArrayList<>(List.of(
        new Enrollment(user, session),
        new Enrollment(new NsUser(2L, "u2", "pw", "name", "email"), session)
    )));

    Payment payment = new Payment("id", session, user, 10000L);

    assertThatThrownBy(() -> policy.checkEnrollAvailability(session, payment))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("정원이 초과되었습니다.");
  }

  @Test
  void 결제금액이_다르면_예외발생() {
    Payment wrongPayment = new Payment("id", session, user, 5000L);

    assertThatThrownBy(() -> policy.checkEnrollAvailability(session, wrongPayment))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("결제 정보가 유효하지 않습니다.");
  }

  @Test
  void 결제정보가_null이면_예외발생() {
    assertThatThrownBy(() -> policy.checkEnrollAvailability(session, null))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("결제 정보가 유효하지 않습니다.");
  }

}