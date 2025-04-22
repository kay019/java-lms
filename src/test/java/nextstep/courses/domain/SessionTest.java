package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionTest {
  private static final Course course = new Course("백엔드 과정", 1L);

  private Session createPaidSession(int capacity, long price, int preEnrolledCount) {
    PaidEnrollmentPolicy policy = new PaidEnrollmentPolicy(capacity, price);
    Session session = new Session(course, "유료강의", LocalDateTime.now(), LocalDateTime.now().plusDays(30), policy);
    NsUser user = createUser();
    session.openForEnrollment();
    for (int i = 0; i < preEnrolledCount; i++) {
      session.enroll(user, new Payment(session, user, price));
    }
    return session;
  }

  private Session createFreeSession() {
    FreeEnrollmentPolicy policy = new FreeEnrollmentPolicy();
    Session session = new Session(course, "무료강의", LocalDateTime.now(), LocalDateTime.now().plusDays(30), policy);
    session.openForEnrollment();
    return session;
  }

  private NsUser createUser() {
    return new NsUser(100L, "user1", "pw", "홍길동", "hong@test.com");
  }

  @Test
  void 유료강의_정상결제시_수강신청_성공() {
    Session session = createPaidSession(10, 10000, 0);
    NsUser user = createUser();
    Payment payment = new Payment(session, user, 10000L);

    session.enroll(user, payment);

    assertEquals(1, session.enrolledCount());
  }

  @Test
  void 유료강의_결제금액_불일치시_예외발생() {
    Session session = createPaidSession(10, 10000, 0);
    NsUser user = createUser();
    Payment wrongPayment = new Payment(session, user, 5000L);

    assertThatThrownBy(() -> session.enroll(user, wrongPayment))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("결제 금액이 일치하지 않습니다.");
  }

  @Test
  void 유료강의_정원초과시_예외발생() {
    Session session = createPaidSession(1, 10000, 1);
    NsUser user = createUser();
    Payment payment = new Payment(session, user, 10000L);

    assertThatThrownBy(() -> session.enroll(user, payment))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("모집중인 상태가 아닙니다.");
  }

  @Test
  void 무료강의는_수강신청_성공() {
    Session session = createFreeSession();
    NsUser user = createUser();

    session.enroll(user, null);

    assertEquals(1, session.enrolledCount());
  }

  @Test
  void 모집중이_아니면_예외발생() {
    PaidEnrollmentPolicy policy = new PaidEnrollmentPolicy(10, 10000);
    Session session = new Session(course, "유료강의", LocalDateTime.now(), LocalDateTime.now().plusDays(30), policy);
    NsUser user = createUser();
    Payment payment = new Payment(session, user, 10000L);

    assertThatThrownBy(() -> session.enroll(user, payment))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("모집중인 상태가 아닙니다.");
  }

}