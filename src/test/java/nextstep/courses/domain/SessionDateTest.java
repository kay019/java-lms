package nextstep.courses.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionDateTest {

    @Test
    @DisplayName("정상 생성 테스트")
    public void sessionDateTest() {
        SessionDate sessionDate = new SessionDate(
            LocalDateTime.parse("2025-04-10"),
            LocalDateTime.parse("2025-04-15")
        );
    }
}
