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
