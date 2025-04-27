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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EnrollmentTest {
    @Test
    void createTest() {
        Enrollment enrollment = new Enrollment(SessionStatus.ONGOING,
                new Students(5L, List.of(new Student(JAVAJIGI, createSession()))));

        assertThat(enrollment).isEqualTo(
                new Enrollment(SessionStatus.ONGOING,
                        new Students(5L, List.of(new Student(JAVAJIGI, createSession())))));
    }

    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다.")
    @ParameterizedTest
    @CsvSource({"READY", "CLOSED"})
    void enrollProgressInvalidTest(SessionStatus sessionStatus) {
        Enrollment enrollment = new Enrollment(sessionStatus,
                new Students(5L, List.of(new Student(JAVAJIGI, createSession()))));

        assertThatThrownBy(() -> enrollment.enroll(new NsUser()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("session is not in progress");
    }

    @Test
    @DisplayName("강의 상태가 모집중일 때는 검증을 통과한다")
    void enrollValidationTest() {
        Enrollment enrollment = new Enrollment(SessionStatus.ONGOING,
                new Students(5L, List.of(new Student(JAVAJIGI, createSession()))));

        assertThatCode(() -> enrollment.enroll(new NsUser(2L, "userId", "password", "name", "email")))
                .doesNotThrowAnyException();
    }
}
