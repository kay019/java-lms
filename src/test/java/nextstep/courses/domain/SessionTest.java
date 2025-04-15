package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {

    public static final Session DEFAULT_SESSION = Session.createFreeSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now());

    @Test
    @DisplayName("시작일과 종료일은 필수 값입니다.")
    void startDateAndEndDateMustBeRequired() {
        assertThatThrownBy(() -> {
            Session.createFreeSession(ImageTest.DEFAULT_IMAGE, null, LocalDate.now().plusDays(1));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시작일은 종료일 이전이어야 합니다.")
    void startDateMustBeBeforeEndDate() {
        assertThatThrownBy(() -> {
            Session.createFreeSession(ImageTest.DEFAULT_IMAGE, LocalDate.now().plusDays(1), LocalDate.now());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("강의는 커버 이미지가 있어야 한다.")
    void courseMustHaveCoverImage() {
        assertThatThrownBy(() -> {
            Session.createFreeSession(null, LocalDate.now(), LocalDate.now());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유료 강의는 최대 참여자 수와 가격을 설정해야 한다.")
    void paidSessionMustHaveMaxParticipantsAndPrice() {
        assertThatThrownBy(() -> {
            Session.createPaidSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now(), null, null);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("최대 참여자 수는 1 이상이어야 한다.")
    void maxParticipantsMustBeGreaterThanZero() {
        assertThatThrownBy(() -> {
            Session.createPaidSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now(), 0, 10000L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격은 0 이상이어야 한다.")
    void priceMustBeGreaterThanZero() {
        assertThatThrownBy(() -> {
            Session.createPaidSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now(), 10, 0L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("참여자 수를 초과할 수 없다.")
    void participantsMustNotExceedMaxParticipants() {
        Session session = Session.createPaidSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now(), 1, 10000L);
        session.enroll(1L, 10000L);
        assertThatThrownBy(() -> {
            session.enroll(2L, 10000L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가격이 일치하지 않으면 참여할 수 없다.")
    void priceMustMatch() {
        Session session = Session.createPaidSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now(), 1, 10000L);
        session.enroll(1L, 10000L);
        assertThatThrownBy(() -> {
            session.enroll(2L, 9999L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("무료 강의는 참여할 수 있다.")
    void freeSessionMustBeParticipant() {
        Session session = Session.createFreeSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now());
        session.enroll(1L, 0L);
        assertThat(session.isParticipant(1L)).isTrue();
    }

    @Test
    @DisplayName("참여자 수와 가격을 맞추면 유료 강의에 참여할 수 있다.")
    void paidSessionMustBeParticipant() {
        Session session = Session.createPaidSession(ImageTest.DEFAULT_IMAGE, LocalDate.now(), LocalDate.now(), 10, 10000L);
        session.enroll(1L, 10000L);
        assertThat(session.isParticipant(1L)).isTrue();
    }
}
