package nextstep.courses.domain;


import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EnrollmentsTest {

  @Test
  void 중복된_수강신청일때_예외발생() {
    NsUser user = new NsUser();
    Session session = new Session(new Course(), "자바", LocalDateTime.now(), LocalDateTime.now(), new PaidEnrollmentPolicy(10, 10000));
    Enrollment enrollment = new Enrollment(user, session.id());
    Enrollment duplicateEnrollment = new Enrollment(user, session.id());
    Enrollments enrollments = new Enrollments(List.of(enrollment));

    assertThatThrownBy(() -> enrollments.add(duplicateEnrollment))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("이미 수강신청한 사용자입니다.");
  }
}