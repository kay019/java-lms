package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class RegistryTest {
    @DisplayName("강의는 모집 중에만 등록 가능")
    @Test
    public void register_session_out_recruiting() {
        assertThatThrownBy(() -> Registry.registerSession(NsUserTest.JAVAJIGI,
                0L,
                new NaturalNumber(1000L),
                SessionState.PREPARING,
                new FreePayStrategy(),
                Arrays.asList(
                        new NsStudent(NsUserTest.SANJIGI.getId(), 0L)
                )))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의는 모집 중일 때만 등록할 수 있습니다.");
    }

    @DisplayName("강의에 이미 등록한 사람이 또 등록은 불가능")
    @Test
    public void cannot_repeat_register() {
        assertThatThrownBy(() -> Registry.registerSession(NsUserTest.JAVAJIGI,
                0L,
                new NaturalNumber(1000L),
                SessionState.RECRUTING,
                new FreePayStrategy(),
                Arrays.asList(
                        new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L)
                )))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("이미 이 강의에 등록한 사람입니다.");
    }

    @DisplayName("무료 강의는 모집 중에 등록 시 아무 조건 없이 등록 가능")
    @Test
    public void register_free_session() {
        assertThatNoException().isThrownBy(() -> Registry.registerSession(NsUserTest.JAVAJIGI,
                0L,
                new NaturalNumber(1000L),
                SessionState.RECRUTING,
                new FreePayStrategy(),
                Arrays.asList(
                        new NsStudent(NsUserTest.SANJIGI.getId(), 0L)
                )));
    }

    @DisplayName("유료 강의는 등록 시 강의 가격과 결제 가격이 같으면 등록 가능")
    @Test
    public void register_paid_session() {
        assertThatNoException().isThrownBy(() -> Registry.registerSession(NsUserTest.JAVAJIGI,
                0L,
                new NaturalNumber(1000L),
                SessionState.RECRUTING,
                new PaidPayStrategy(1000L, 5L),
                Arrays.asList(
                        new NsStudent(NsUserTest.SANJIGI.getId(), 0L)
                )));
    }
}
