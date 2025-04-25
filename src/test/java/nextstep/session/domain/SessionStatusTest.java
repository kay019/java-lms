package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStatusTest {

    @Test
    @DisplayName("모집 상태가 모집중이고 진행 상태가 종료가 아니면 수강신청 가능")
    void testIsEnrollmentAvailableTrue() {
        SessionStatus status = new SessionStatus(SessionProgressStatus.IN_PROGRESS, EnrollmentStatus.ENROLLING);
        assertThat(status.isEnrollmentAvailable()).isTrue();
        assertThat(status.isEnrollmentUnavailable()).isFalse();
    }

    @Test
    @DisplayName("모집 상태가 비모집중이면 수강신청 불가능")
    void testIsEnrollmentAvailableFalseWhenNotEnrolling() {
        SessionStatus status = new SessionStatus(SessionProgressStatus.IN_PROGRESS, EnrollmentStatus.NOT_ENROLLING);
        assertThat(status.isEnrollmentAvailable()).isFalse();
        assertThat(status.isEnrollmentUnavailable()).isTrue();
    }

    @Test
    @DisplayName("진행 상태가 종료이면 수강신청 불가능")
    void testIsEnrollmentAvailableFalseWhenClosed() {
        SessionStatus status = new SessionStatus(SessionProgressStatus.CLOSED, EnrollmentStatus.ENROLLING);
        assertThat(status.isEnrollmentAvailable()).isFalse();
        assertThat(status.isEnrollmentUnavailable()).isTrue();
    }

    @Test
    @DisplayName("문자열 생성자로 올바른 Enum 값이 초기화 되는지 확인")
    void testConstructorWithString() {
        SessionStatus status = new SessionStatus("IN_PROGRESS", "ENROLLING");
        assertThat(status.getProgressStatusName()).isEqualTo("IN_PROGRESS");
        assertThat(status.getEnrollmentStatusName()).isEqualTo("ENROLLING");
    }

    @Test
    @DisplayName("Getter 메서드가 올바른 Enum 이름을 반환하는지 확인")
    void testGetters() {
        SessionStatus status = new SessionStatus(SessionProgressStatus.PREPARING, EnrollmentStatus.NOT_ENROLLING);
        assertThat(status.getProgressStatusName()).isEqualTo("PREPARING");
        assertThat(status.getEnrollmentStatusName()).isEqualTo("NOT_ENROLLING");
    }
}
