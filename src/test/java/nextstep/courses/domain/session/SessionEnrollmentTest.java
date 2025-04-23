package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import nextstep.courses.domain.session.inner.UserEnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SessionEnrollmentTest {
    public static final SessionEnrollment SESSION_ENROLLMENT1 = new SessionEnrollment(1L, 1L, 1L, UserEnrollmentStatus.REQUESTED);

    private static final Long SESSION_ID = 1L;
    private static final Long USER_ID = 100L;

    @Test
    void 요청_생성() {
        SessionEnrollment enrollment = SessionEnrollment.requestEnroll(SESSION_ID, USER_ID);

        assertThat(enrollment.getId()).isNull();
        assertThat(enrollment.getSessionId()).isEqualTo(SESSION_ID);
        assertThat(enrollment.getNsUserId()).isEqualTo(USER_ID);
        assertThat(enrollment.getStatus()).isEqualTo(UserEnrollmentStatus.REQUESTED);
        assertThat(enrollment.isAvailable()).isTrue();
    }

    @Test
    void 요청_불가() {
        SessionEnrollment enrollment = SessionEnrollment.notAvailableEnroll(SESSION_ID, USER_ID);

        assertThat(enrollment.getId()).isNull();
        assertThat(enrollment.getSessionId()).isEqualTo(SESSION_ID);
        assertThat(enrollment.getNsUserId()).isEqualTo(USER_ID);
        assertThat(enrollment.getStatus()).isEqualTo(UserEnrollmentStatus.NOT_AVAILABLE);
        assertThat(enrollment.isAvailable()).isFalse();
    }

    @Test
    void 요청_승인() {
        SessionEnrollment enrollment = SessionEnrollment.requestEnroll(SESSION_ID, USER_ID);

        enrollment.approve();

        assertThat(enrollment.getStatus()).isEqualTo(UserEnrollmentStatus.ENROLLED);
    }

    @Test
    void 요청중_아닌데_승인() {
        SessionEnrollment enrollment = SessionEnrollment.notAvailableEnroll(SESSION_ID, USER_ID);

        assertThatThrownBy(enrollment::approve)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot approve enrollment that is not requested");
    }

    @Test
    void 요청_거절() {
        SessionEnrollment enrollment = SessionEnrollment.requestEnroll(SESSION_ID, USER_ID);

        enrollment.reject();

        assertThat(enrollment.getStatus()).isEqualTo(UserEnrollmentStatus.REJECTED);
    }

    @Test
    void 요청중_아닌데_거절() {
        SessionEnrollment enrollment = SessionEnrollment.notAvailableEnroll(SESSION_ID, USER_ID);

        assertThatThrownBy(enrollment::reject)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot approve enrollment that is not requested");
    }

    @MethodSource("availabilityTestCases")
    void 요청성공(String scenario, boolean expected, SessionEnrollment enrollment) {
        // when & then
        assertThat(enrollment.isAvailable()).isEqualTo(expected);
    }

    static Stream<Arguments> availabilityTestCases() {
        final Long SESSION_ID = 1L;
        final Long USER_ID = 100L;

        // Not Available 상태 등록
        SessionEnrollment notAvailableEnrollment = SessionEnrollment.notAvailableEnroll(SESSION_ID, USER_ID);

        // Requested 상태 등록
        SessionEnrollment requestedEnrollment = SessionEnrollment.requestEnroll(SESSION_ID, USER_ID);

        // Approved 상태 등록
        SessionEnrollment approvedEnrollment = SessionEnrollment.requestEnroll(SESSION_ID, USER_ID);
        approvedEnrollment.approve();

        return Stream.of(
                Arguments.of("NOT_AVAILABLE 상태", false, notAvailableEnrollment),
                Arguments.of("REQUESTED 상태", true, requestedEnrollment),
                Arguments.of("승인 후 ENROLLED 상태", true, approvedEnrollment)
        );
    }
}