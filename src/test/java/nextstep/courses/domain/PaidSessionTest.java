package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import nextstep.payments.domain.PaymentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaidSessionTest {

    @Test
    @DisplayName("유료 강의는 최대 참여자 수와 가격을 설정해야 한다.")
    void paidSessionMustHaveMaxParticipantsAndPrice() {
        assertThatThrownBy(() -> {
            new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), null, null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최대 참여자 수는 1 이상이어야 한다.")
    void maxParticipantsMustBeGreaterThanZero() {
        assertThatThrownBy(() -> {
            new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), 0, 10000L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격은 0 이상이어야 한다.")
    void priceMustBeGreaterThanZero() {
        assertThatThrownBy(() -> {
            new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), 10, 0L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("참여자 수를 초과할 수 없다.")
    void participantsMustNotExceedMaxParticipants() {
        Session session = new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), 1, 1000L);
        session.openEnrollment();
        session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        assertThatThrownBy(() -> {
            session.enroll(2L, PaymentTest.DEFAULT_PAYMENT);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("참여자 수와 가격을 맞추면 유료 강의에 참여할 수 있다.")
    void paidSessionMustBeParticipant() {
        Session session = new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), 10, 1000L);
        session.openEnrollment();
        session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        assertThat(session.isParticipant(1L)).isTrue();
    }

    @Test
    @DisplayName("가격이 일치하지 않으면 참여할 수 없다.")
    void priceMustMatch() {
        Session session = new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), 1, 10000L);
        session.openEnrollment();
        assertThatThrownBy(() -> {
            session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}