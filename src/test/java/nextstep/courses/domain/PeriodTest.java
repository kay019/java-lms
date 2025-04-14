package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PeriodTest {
    @DisplayName("끝나는 날짜가 시작 날짜보다 앞일 경우 기간 생성 오류")
    @Test
    public void create_session_period_error() {
        assertThatThrownBy(() ->
                new Period(LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                        , LocalDateTime.of(2025, Month.APRIL, 10, 15, 29)))
                .isInstanceOf(InvalidPeriodException.class)
                .hasMessage("기간의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
    }
}
