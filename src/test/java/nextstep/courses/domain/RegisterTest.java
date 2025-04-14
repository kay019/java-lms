package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegisterTest {
    @DisplayName("강의는 모집 중에만 등록 가능")
    @Test
    public void register_session_out_recruiting() {
        assertThatThrownBy(() -> new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.FINISHED
                , new FreePayStrategy()
                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의는 모집 중일 때만 등록할 수 있습니다.");
    }

    @DisplayName("무료 강의는 모집 중에 등록 시 아무 조건 없이 등록 가능")
    @Test
    public void register_free_session() {
        assertThat(new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new FreePayStrategy()
                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isEqualTo(new NsStudent(NsUserTest.JAVAJIGI, 0L)
                );
    }

    @DisplayName("유료 강의는 등록 시 강의 가격과 결제 가격이 같으면 등록 가능")
    @Test
    public void register_paid_session() {
        assertThat(new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new PaidPayStrategy(1000L, 5L)
                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isEqualTo(new NsStudent(NsUserTest.JAVAJIGI, 0L)
                );
    }
}
