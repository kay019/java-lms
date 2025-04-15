package nextstep.courses.domain;


import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.courses.domain.strategy.FreeEnrollmentStrategy;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static nextstep.courses.domain.ImageFormat.PNG;
import static nextstep.courses.domain.SessionStatus.OPENED;
import static nextstep.courses.domain.SessionStatus.PREPARING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionTest {
    @Test
    @DisplayName("수강 신청 - 상태가 PREPARING이 아닌경우 예외")
    public void enrollTest() {
        Session session = getSession(PREPARING);
        Payment payment = new Payment();

        assertThatThrownBy(() -> session.enroll(NsUserTest.JAVAJIGI, payment))
            .hasMessageContaining("강의 상태가 PREPARING이 아닙니다.");
    }

    @Test
    @DisplayName("수강 제한 인원보다 등록인원이 같은경우 - true")
    public void isExceedLimitEnrollmentCountTest() {
        Session session = getSession(OPENED);
        Payment payment = new Payment();
        session.enroll(NsUserTest.JAVAJIGI, payment);

        assertThat(session.isExceedLimitEnrollmentCount(1)).isTrue();
    }

    @Test
    @DisplayName("Fee가 같은 경우 - true")
    public void isSameFeeTest() {
        Session session = getSession(100);
        Payment payment = new Payment("1", NsUserTest.JAVAJIGI, 100L);

        assertThat(session.isSameFee(payment)).isTrue();
    }


    public static Session getSession(SessionStatus status) {
        return new Session(
            100,
            status,
            new FreeEnrollmentStrategy(),
            CoverImageTest.getCoverImage(PNG),
            new SessionDate(LocalDateTime.parse("2025-04-10"),
                LocalDateTime.parse("2025-04-15"))
        );
    }

    public static Session getSession(int fee) {
        return new Session(
            fee,
            PREPARING,
            new FreeEnrollmentStrategy(),
            CoverImageTest.getCoverImage(PNG),
            new SessionDate(LocalDateTime.parse("2025-04-10"),
                LocalDateTime.parse("2025-04-15"))
        );
    }

}
