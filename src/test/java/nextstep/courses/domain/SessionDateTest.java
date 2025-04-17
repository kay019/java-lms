package nextstep.courses.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.exception.SessionDateIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionDateTest {
    @Test
    @DisplayName("시작일이 종료일보다 이전인 경우 정상 생성")
    void createWithValidDatesStartBeforeEnd() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        SessionDate sessionDate = new SessionDate(startDate, endDate);

        assertThat(sessionDate.getStartDate()).isEqualTo(startDate);
        assertThat(sessionDate.getEndDate()).isEqualTo(endDate);
    }

    @Test
    @DisplayName("시작일과 종료일이 동일한 경우 정상 생성")
    void createWithValidDatesStartEqualEnd() {
        LocalDate date = LocalDate.of(2023, 1, 1);

        SessionDate sessionDate = new SessionDate(date, date);

        assertThat(sessionDate.getStartDate()).isEqualTo(date);
        assertThat(sessionDate.getEndDate()).isEqualTo(date);
    }

    @Test
    @DisplayName("시작일이 종료일보다 이후인 경우 예외 발생")
    void createWithInvalidDatesStartAfterEnd() {
        LocalDate startDate = LocalDate.of(2023, 1, 10);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        assertThatThrownBy(() -> new SessionDate(startDate, endDate))
            .isInstanceOf(SessionDateIllegalArgumentException.class);
    }
}
