package nextstep.qna.domain;

import nextstep.courses.domain.Period;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodTest {
    @Test
    @DisplayName("종료일이 시작일보다 이르거나 같으면 예외가 발생한다")
    void invalidPeriodThrowsException() {
        LocalDate startDate = LocalDate.of(2025, 4, 10);
        LocalDate endDateSame = LocalDate.of(2025, 4, 10);
        LocalDate endDateBefore = LocalDate.of(2025, 4, 9);

        assertThatThrownBy(() -> new Period(startDate, endDateSame))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료일은 시작일보다 이후여야 합니다.");

        assertThatThrownBy(() -> new Period(startDate, endDateBefore))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료일은 시작일보다 이후여야 합니다.");
    }
}