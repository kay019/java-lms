package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SessionDateTest {
    @Test
    @DisplayName("시작일이 종료일보다 늦으면 예외를 발생한다.")
    void validateTest() {
        LocalDateTime startedAt = LocalDateTime.of(2025, 5, 1, 0, 0);
        LocalDateTime endedAt = LocalDateTime.of(2025, 4, 1, 0, 0);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionDate(startedAt, endedAt));
    }

}
