package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionPeriodTest {

    @Test
    @DisplayName("시작일과 종료일이 유효하면 SessionPeriod 객체를 생성할 수 있다")
    void createSessionPeriodWithValidDates() {
        // given
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(7);

        // when
        CourseSession.SessionPeriod sessionPeriod = new CourseSession.SessionPeriod(startDate, endDate);

        // then
        assertThat(sessionPeriod).isNotNull();
    }

    @Test
    @DisplayName("종료일이 시작일과 동일하면 SessionPeriod 객체를 생성할 수 있다")
    void createSessionPeriodWithSameDates() {
        // given
        LocalDateTime sameDate = LocalDateTime.now();

        // when
        CourseSession.SessionPeriod sessionPeriod = new CourseSession.SessionPeriod(sameDate, sameDate);

        // then
        assertThat(sessionPeriod).isNotNull();
    }

    @Test
    @DisplayName("종료일이 시작일보다 이전이면 예외가 발생한다")
    void throwExceptionWhenEndDateIsBeforeStartDate() {
        // given
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(1);

        // when & then
        assertThatThrownBy(() -> new CourseSession.SessionPeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료일은 시작일보다 이전일 수 없습니다.");
    }
} 