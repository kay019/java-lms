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
                        , new FreeRegisterStrategy()
                        , new Image(1000L, ImageType.GIF, 300L, 200L))
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
                        , new FreeRegisterStrategy()
                        , new Image(1000L, ImageType.GIF, 300L, 200L)))
                .isInstanceOf(CannotCreateSessionException.class)
                .hasMessage("강의의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
    }

    @DisplayName("강의는 모집 중에만 등록 가능")
    @Test
    public void register_session_out_recruiting() {
        assertThatThrownBy(() -> new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.FINISHED
                , new FreeRegisterStrategy()
                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의는 모집 중일 때만 등록할 수 있습니다.");
    }

    @DisplayName("강의에 이미 등록한 사람이 또 등록은 불가능")
    @Test
    public void cannot_repeat_register() {
        Session session = new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new FreeRegisterStrategy()
                , new Image(1000L, ImageType.GIF, 300L, 200L));
        session.register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L));
        assertThatThrownBy(() -> session.register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("이미 이 강의에 등록한 사람입니다.");
    }

    @DisplayName("무료 강의는 모집 중에 등록 시 아무 조건 없이 등록 가능")
    @Test
    public void register_free_session() {
        assertThat(new Session(0L
                                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                                , SessionState.RECRUTING
                                , new FreeRegisterStrategy()
                                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isEqualTo(new Payment("", 0L, NsUserTest.JAVAJIGI.getId(), 0L)
                );
    }

    @DisplayName("유료 강의는 등록 시 강의 가격과 결제 가격이 같으면 등록 가능")
    @Test
    public void register_paid_session() {
        assertThat(new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new PaidRegsiterStrategy(1000L, 5L)
                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L)))
                .isEqualTo(new Payment("", 0L, NsUserTest.JAVAJIGI.getId(), 1000L)
                );
    }

    @DisplayName("유료 강의는 등록 시 강의 가격과 결제 가격이 같지 않으면 등록 불가능")
    @Test
    public void cannot_register_paid_session() {
        assertThatThrownBy(() -> new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new PaidRegsiterStrategy(1000L, 5L)
                , new Image(1000L, ImageType.GIF, 300L, 200L)).register(NsUserTest.JAVAJIGI, new NaturalNumber(500L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("결제 금액이 강의의 가격과 같지 않습니다.");
    }

    @DisplayName("유료 강의는 등록 시 수강 정원이 초과되면 등록 불가능")
    @Test
    public void cannot_register_paid_session_over_capacity() {
        Session session = new Session(0L
                , LocalDateTime.of(2025, Month.APRIL, 10, 15, 30)
                , LocalDateTime.of(2025, Month.APRIL, 11, 15, 30)
                , SessionState.RECRUTING
                , new PaidRegsiterStrategy(1000L, 1L)
                , new Image(1000L, ImageType.GIF, 300L, 200L));
        session.register(NsUserTest.JAVAJIGI, new NaturalNumber(1000L));
        assertThatThrownBy(() -> session.register(NsUserTest.SANJIGI, new NaturalNumber(1000L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("해당 강의의 수강 정원이 모두 마감되었습니다.");
    }

}
