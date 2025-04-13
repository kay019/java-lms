package nextstep.sessions;

import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.Period;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {
    @Test
    void testCreateSession() {
        Period sessionPeriod = new Period(
                LocalDate.of(2025, 4, 13),
                LocalDate.of(2025, 4, 14)
        );
        assertThat(new Session(sessionPeriod)).isEqualTo(new Session(sessionPeriod));
    }
}
