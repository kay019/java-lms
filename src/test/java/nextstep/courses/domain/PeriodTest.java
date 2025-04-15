package nextstep.courses.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;   
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodTest {

    @DisplayName("시작일은 종료일보다 이전이어야 한다.")
    @Test
    void testStartDateBeforeEndDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Period period = new Period(startDate, endDate);
        assertThat(period).isNotNull();

        assertThatThrownBy(() -> new Period(endDate, startDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }

    @DisplayName("시작일과 종료일은 필수 값이어야 한다.")
    @Test
    void testStartDateAndEndDateAreRequired() {
        LocalDate startDate = null;
        LocalDate endDate = null;
        assertThatThrownBy(() -> new Period(startDate, endDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작일과 종료일은 필수 값입니다.");
    }
}