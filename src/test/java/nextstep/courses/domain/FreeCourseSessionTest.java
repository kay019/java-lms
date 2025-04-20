package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FreeCourseSessionTest {
    @DisplayName("무료 강의 신청 성공")
    @Test
    public void successRegister() throws Exception {
        CourseSession freeCourseSession = new FreeCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThat(freeCourseSession.register(1L))
                .isEqualTo(1L);

        assertThat(freeCourseSession.registeredUserSize())
                        .isEqualTo(1);

        assertThat(freeCourseSession.register(2L))
                .isEqualTo(2L);

        assertThat(freeCourseSession.registeredUserSize())
                .isEqualTo(2);
    }

    @DisplayName("무료 강의 중복 신청")
    @Test
    public void failedRegister() throws Exception {
        CourseSession freeCourseSession = new FreeCourseSession(1L, new SessionCoverImage("jpg", 300, 200, 10), SessionStatus.PREPARING, "test", new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThat(freeCourseSession.register(1L))
                .isEqualTo(1L);

        assertThatThrownBy(() -> freeCourseSession.register(1L))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageStartingWith("This user has already enrolled in the course.");

    }
}