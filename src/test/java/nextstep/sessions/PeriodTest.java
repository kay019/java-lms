package nextstep.sessions;

import nextstep.sessions.domain.Period;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PeriodTest {
    @ParameterizedTest
    @CsvSource(value={
            "2025-04-15,2025-04-15",
            "2025-04-15,2025-04-16",
            "2025-04-15,2026-04-15"
    })
    void testCreatePeriod(LocalDate startDate, LocalDate endDate) {
        assertThat(new Period(startDate, endDate))
                .isNotNull();
    }

    @Test
    void testInvalidPeriod() {
        assertThatThrownBy(() ->
            new Period(
                    LocalDate.of(2025, 4, 15),
                    LocalDate.of(2025, 4, 14)
            )
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
