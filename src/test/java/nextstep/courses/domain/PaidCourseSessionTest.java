package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidCourseSessionTest {
    @DisplayName("유료 강의 신청 성공")
    @Test
    public void successRegister() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThat(paidCourseSession.register(1L, new Payment("1", 0L, 1L, 1L, 1000L)))
                .isEqualTo(1L);

        assertThat(paidCourseSession.registeredUserSize())
                .isEqualTo(1);

        assertThat(paidCourseSession.register(2L, new Payment("1", 0L, 1L, 2L, 1000L)))
                .isEqualTo(2L);

        assertThat(paidCourseSession.registeredUserSize())
                .isEqualTo(2);
    }

    @DisplayName("유료 강의 중복 신청")
    @Test
    public void failedRegisterDuplicate() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThat(paidCourseSession.register(1L, new Payment("1", 0L, 1L, 1L, 1000L)))
                .isEqualTo(1L);

        assertThat(paidCourseSession.registeredUserSize())
                .isEqualTo(1);

        assertThatThrownBy(() -> paidCourseSession.register(1L, new Payment("1", 0L, 1L, 1L, 1000L)))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageStartingWith("This user has already enrolled in the course.");
    }

    @DisplayName("유료 강의 결제 정보 없음")
    @Test
    public void failedRegisterNoPayment() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThatThrownBy(() -> paidCourseSession.register(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("Payment cannot be null");
    }

    @DisplayName("유료 강의 결제 정보 sessionId 불일치")
    @Test
    public void failedRegisterSessionMisMatched() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThatThrownBy(() -> paidCourseSession.register(1L, new Payment("1", 0L, 2L, 1L, 1000L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Session id mismatch");
    }

    @DisplayName("유료 강의 결제 정보 유저 불일치")
    @Test
    public void failedRegisterUserMisMatched() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThatThrownBy(() -> paidCourseSession.register(2L, new Payment("1", 0L, 1L, 1L, 1000L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("NsUserId mismatch");
    }

    @DisplayName("유료 강의 정원 초과")
    @Test
    public void failedRegisterMaxCapacity() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThat(paidCourseSession.register(1L, new Payment("1", 0L, 1L, 1L, 1000L)))
                .isEqualTo(1L);

        assertThat(paidCourseSession.registeredUserSize())
                .isEqualTo(1);

        assertThat(paidCourseSession.register(2L, new Payment("1", 0L, 1L, 2L, 1000L)))
                .isEqualTo(2L);

        assertThat(paidCourseSession.registeredUserSize())
                .isEqualTo(2);

        assertThatThrownBy(() -> paidCourseSession.register(3L, new Payment("1", 0L, 1L, 3L, 1000L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("This class has reached its maximum capacity.");
    }

    @DisplayName("유료 강의 결제 정보 가격 불일치")
    @Test
    public void failedRegisterPrice() throws Exception {
        CourseSession paidCourseSession = new PaidCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", 2, 1000, new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThatThrownBy(() -> paidCourseSession.register(1L, new Payment("1", 0L, 1L, 1L, 2000L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Payment amount is not equal to price amount.");
    }
}