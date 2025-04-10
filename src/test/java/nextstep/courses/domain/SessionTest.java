package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;

public class SessionTest {
    @DisplayName("강의 정상 생성")
    @Test
    public void create_session() {
        assertThatNoException().isThrownBy(() ->
                new Session(0L
                        , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                        , LocalDateTime.of(2025, Month.APRIL, 15, 15, 30)
                        , SessionState.RECRUTING
                        , new FreeRegisterStrategy())
        );
    }

    @DisplayName("끝나는 날짜가 시작 날짜보다 앞일 경우 강의 생성 오류")
    @Test
    public void create_session_period_error() {
        assertThatThrownBy(() ->
                new Session(0L
                        , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                        , LocalDateTime.of(2025, Month.APRIL, 10, 15, 29)
                        , SessionState.RECRUTING
                        , new FreeRegisterStrategy()))
                .isInstanceOf(CannotCreateSessionException.class)
                .hasMessage("강의의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
    }

    @DisplayName("무료 강의는 등록 시 아무 조건 없이 바로 등록 가능")
    @Test
    public void register_free_session() {
        assertThat(new Session(0L
                                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                                , SessionState.RECRUTING
                                , new FreeRegisterStrategy()
                    ).register(NsUserTest.JAVAJIGI, 1000L))
                .isEqualTo(new Payment("", 0L, NsUserTest.JAVAJIGI.getId(), 0L)
                );
    }
}
