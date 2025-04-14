package nextstep.sessions;

import nextstep.sessions.domain.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PeriodTest {
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
