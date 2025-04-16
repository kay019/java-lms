package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

public class RegistryTest {
    @DisplayName("강의는 모집 중에만 등록 가능")
    @Test
    public void register_session_out_recruiting() {
        Registry registry = new Registry(new FreePayStrategy(), SessionState.PREPARING, new PositiveNumber(1000000L));
        assertThatThrownBy(() -> registry.register(NsUserTest.JAVAJIGI, 0L, new PositiveNumber(500L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의는 모집 중일 때만 등록할 수 있습니다.");
    }

    @DisplayName("무료 강의는 모집 중에 등록 시 아무 조건 없이 등록 가능")
    @Test
    public void register_free_session() {
        Registry registry = new Registry(new FreePayStrategy(), SessionState.RECRUTING, new PositiveNumber(1000000L));
        assertThatNoException().isThrownBy(() -> registry.register(NsUserTest.JAVAJIGI, 0L, new PositiveNumber(0L)));
    }

    @DisplayName("유료 강의는 등록 시 강의 가격과 결제 가격이 같으면 등록 가능")
    @Test
    public void register_paid_session() {
        Registry registry = new Registry(new PaidPayStrategy(1000L), SessionState.RECRUTING, new PositiveNumber(1000000L));
        assertThatNoException().isThrownBy(() -> registry.register(NsUserTest.JAVAJIGI, 0L, new PositiveNumber(1000L)));
    }
}
