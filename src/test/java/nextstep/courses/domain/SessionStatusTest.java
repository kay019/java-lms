package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStatusTest {

    @Test
    @DisplayName("준비 중 강의만 개강할 수 있다.")
    void preparingCourseMustBeEnrolled() {
        SessionStatus status = SessionStatus.PREPARING;
        assertThat(status.openEnrollment()).isEqualTo(SessionStatus.ENROLLING);

        assertThatThrownBy(() -> {
            SessionStatus.ENROLLING.openEnrollment();
        }).isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> {
            SessionStatus.CLOSED.openEnrollment();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("참여 중 강의만 종료할 수 있다.")
    void enrollingCourseMustBeClosed() {
        SessionStatus status = SessionStatus.ENROLLING;
        assertThat(status.closeEnrollment()).isEqualTo(SessionStatus.CLOSED);

        assertThatThrownBy(() -> {
            SessionStatus.PREPARING.closeEnrollment();
        }).isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> {
            SessionStatus.CLOSED.closeEnrollment();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("참여 중 강의만 수강신청할 수 있다.")
    void enrollingCourseMustBeEnrolled() {
        SessionStatus status = SessionStatus.ENROLLING;
        status.assertCanEnroll();

        assertThatThrownBy(() -> {
            SessionStatus.PREPARING.assertCanEnroll();
        }).isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(() -> {
            SessionStatus.CLOSED.assertCanEnroll();
        }).isInstanceOf(IllegalStateException.class);
    }

}