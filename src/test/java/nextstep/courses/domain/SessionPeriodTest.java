package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

class SessionPeriodTest {
    @DisplayName("종료일이 시작일 보다 먼저 나오는 경우")
    @Test
    public void period() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Assertions.assertThatThrownBy(() -> new SessionPeriod(now.plusDays(1), now))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageStartingWith("Start date must be before end date");

        Assertions.assertThatThrownBy(() -> new SessionPeriod(now, now))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageStartingWith("Start date must be before end date");
    }
}