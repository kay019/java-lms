package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import nextstep.payments.domain.PaymentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {

    public static final Session DEFAULT_SESSION = new FreeSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()));

    @Test
    @DisplayName("기간은 필수 값입니다.")
    void periodMustBeRequired() {
        assertThatThrownBy(() -> {
            new FreeSession(ImageTest.DEFAULT_IMAGE, null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("강의는 커버 이미지가 있어야 한다.")
    void courseMustHaveCoverImage() {
        assertThatThrownBy(() -> {
            new FreeSession(null, new Period(LocalDate.now(), LocalDate.now()));
        }).isInstanceOf(IllegalArgumentException.class);
    }

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
    @DisplayName("가격이 일치하지 않으면 참여할 수 없다.")
    void priceMustMatch() {
        Session session = new PaidSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()), 1, 10000L);
        session.openEnrollment();
        assertThatThrownBy(() -> {
            session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("무료 강의는 참여할 수 있다.")
    void freeSessionMustBeParticipant() {
        Session session = new FreeSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()));
        session.openEnrollment();
        session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        assertThat(session.isParticipant(1L)).isTrue();
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
    @DisplayName("강의는 처음에 준비 중 상태이다.")
    void courseMustBePreparing() {
        Session session = new FreeSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()));
        assertThat(session.isPreparing()).isTrue();
    }

    @Test
    @DisplayName("참여 중 강의만 수강할 수 있다.")
    void enrolledCourseMustBeEnrolled() {
        Session session = new FreeSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()));
        assertThatThrownBy(() -> {
            session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        }).isInstanceOf(IllegalStateException.class);
        
        session.openEnrollment();
        session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        assertThat(session.isParticipant(1L)).isTrue();
    }
}
