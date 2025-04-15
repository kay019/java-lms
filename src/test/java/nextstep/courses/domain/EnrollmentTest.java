package nextstep.courses.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrollmentTest {

  @Test
  void 수강신청_생성() {
    NsUser user = new NsUser();
    Session session = new Session(new Course(), "자바", LocalDateTime.now(), LocalDateTime.now(), new PaidEnrollmentPolicy(10, 10000));

    Enrollment enrollment = new Enrollment(user, session);

    assertEquals(enrollment, new Enrollment(user, session));
  }
}