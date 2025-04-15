package nextstep.qna.domain;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.LectureStatus;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {
    @Test
    @DisplayName("모집중이 아닌 경우, 수강신청이 불가능하다")
    void notRegisterSession() {
        Session session = new FreeSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.CLOSED
        );
        assertThatThrownBy(() -> {
            session.register(1L, new Payment());
        }).isInstanceOf(IllegalStateException.class).hasMessage("수강 신청은 모집중일 때만 가능합니다.");
    }
    @Test
    @DisplayName("모집중인 경우, 수강신청이 가능하다")
    void registerSession() {
        Session session = new FreeSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.RECRUITING
        );
        session.register(1L, new Payment());
        assertThat(session.isRegistered(1L)).isTrue();
    }

    @Test
    @DisplayName("유료 강의는 수강 인원이 제한되어 있다.")
    void paidSession() {
        Session session = new PaidSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.RECRUITING,
                0,
                10000L
        );
        assertThatThrownBy(() -> {
            session.register(11L, new Payment(
                    "1",
                    1L,
                    11L,
                    10000L
            ));
        }).isInstanceOf(IllegalStateException.class).hasMessage("수강 인원을 초과하였습니다.");
    }

    @Test
    @DisplayName("유료 강의는 결제 정보가 없다면 수강신청이 불가능하다.")
    void paidSessionWithoutPayment() {
        Session session = new PaidSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.RECRUITING,
                10,
                10000L
        );
        assertThatThrownBy(() -> {
            session.register(11L, null);
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("결제 정보가 없습니다.");
    }

    @Test
    @DisplayName("결제한 강의와 일치하지 않으면, 수강신청이 불가능하다.")
    void paidSessionWithDifferentPayment() {
        Session session = new PaidSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.RECRUITING,
                10,
                10000L
        );
        assertThatThrownBy(() -> {
            session.register(11L, new Payment(
                    "1",
                    2L,
                    11L,
                    10000L
            ));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("결제한 강의와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("결제한 사용와 일치하지 않으면 수강신청이 불가능하다.")
    void paidSessionWithDifferentStudent() {
        Session session = new PaidSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.RECRUITING,
                10,
                10000L
        );
        assertThatThrownBy(() -> {
            session.register(11L, new Payment(
                    "1",
                    1L,
                    12L,
                    10000L
            ));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("결제한 사용자와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("결제한 금액과 일치하지 않으면 수강신청이 불가능하다.")
    void paidSessionWithDifferentTuitionFee() {
        Session session = new PaidSession(
                1L,
                "자바지기",
                LocalDate.now(),
                LocalDate.now(),
                null,
                LectureStatus.RECRUITING,
                10,
                10000L
        );
        assertThatThrownBy(() -> {
            session.register(11L, new Payment(
                    "1",
                    1L,
                    11L,
                    20000L
            ));
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("결제 금액과 일치하지 않습니다.");
    }
}
