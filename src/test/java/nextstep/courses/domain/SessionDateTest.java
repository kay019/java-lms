package nextstep.courses.domain;

import nextstep.courses.session.domain.SessionDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SessionDateTest {

    @Test
    void 시작일이_종료일보다_이후_예외() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionDate(LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0)));
    }
}
