package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
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
                        , new FreePayStrategy()
                        , new Image(1000L, ImageType.GIF, 300L, 200L))
        );
    }

    @DisplayName("강의에 이미 등록한 사람이 또 등록은 불가능")
    @Test
    public void cannot_repeat_register() {
        Session session = new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new FreePayStrategy()
                , new Image(1000L, ImageType.GIF, 300L, 200L));
        session.register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L));
        assertThatThrownBy(() -> session.register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("이미 이 강의에 등록한 사람입니다.");
    }



}
