package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class SessionTest {
    @DisplayName("강의 정상 생성")
    @Test
    public void create_session() {
        assertThatNoException().isThrownBy(() ->
                new Session(LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                        , LocalDateTime.of(2025, Month.APRIL, 15, 15, 30)
                        , SessionState.RECRUTING
                        , new FreeRegisterStrategy())
        );
    }


}
