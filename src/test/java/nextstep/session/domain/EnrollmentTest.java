package nextstep.session.domain;

import static nextstep.session.SessionTest.createSession;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnrollmentTest {
    @Test
    void createTest() {
        Enrollment enrollment = new Enrollment(SessionStatus.ONGOING,
                EnrollmentStatus.ENROLLED,
                new Students(5L, List.of(new Student(JAVAJIGI, createSession()))));

        assertThat(enrollment).isEqualTo(
                new Enrollment(SessionStatus.ONGOING,
                        EnrollmentStatus.ENROLLED,
                        new Students(5L, List.of(new Student(JAVAJIGI, createSession())))));
    }

    @Test
    @DisplayName("강의 모집상태가 모집중일때 수강신청이 가능하다.")
    void enrollValidationTest() {
        Enrollment enrollment = new Enrollment(SessionStatus.ONGOING, EnrollmentStatus.ENROLLED,
                new Students(5L, List.of(new Student(JAVAJIGI, createSession()))));

        assertThatCode(() -> enrollment.enroll(new NsUser(2L, "userId", "password", "name", "email")))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("강의 상태가 모집중이지 않으면 예외를 발생시킨다.")
    void enrollInvalidTest() {
        Enrollment enrollment = new Enrollment(SessionStatus.ONGOING, EnrollmentStatus.NOT_ENROLLED,
                new Students(5L, List.of(new Student(JAVAJIGI, createSession()))));

        assertThatThrownBy(() -> enrollment.enroll(new NsUser()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("session is not in enrollment");
    }
}
