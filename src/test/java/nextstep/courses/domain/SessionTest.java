package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    void paidSessionEnrollTest() {
        //given
        CoverImage coverImage = new CoverImage(1024 * 1024, ImageType.GIF, 300, 200);
        Session session = Session.register(0L, coverImage, SessionType.PAID, 10000L);
        session.open();
        NsUser user = NsUserTest.JAVAJIGI;
        Payment payment = new Payment("0", session.getId(), user.getId(), 10000L);
        LocalDateTime now = LocalDateTime.now();

        //when
        EnrollmentHistory enroll = session.enroll(user, payment.getAmount(), now);

        //then
        Assertions.assertThat(enroll)
                .isNotNull()
                .extracting("user", "session", "enrolledAt")
                .containsExactly(user, session, now);
    }

    @Test
    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 다른 경우 에러를 던진다.")
    void paidSessionEnrollThrowTest() {
        //given
        CoverImage coverImage = new CoverImage(1024 * 1024, ImageType.GIF, 300, 200);
        Session session = Session.register(0L, coverImage, SessionType.PAID, 10000L);
        session.open();
        NsUser user = NsUserTest.JAVAJIGI;
        Payment payment = new Payment("0", session.getId(), user.getId(), 100L);
        LocalDateTime now = LocalDateTime.now();

        //when&then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> session.enroll(user, payment.getAmount(), now))
                .withMessageContaining("결제 금액이 수강료와 일치하지 않습니다.");

    }

    @Test
    @DisplayName("강의 수강신청은 강의 상태가 모집중이 아닌 경우 에러를 던진다.")
    void enrollClosedSessionThrowTest() {
        // given
        CoverImage coverImage = new CoverImage(1024 * 1024, ImageType.GIF, 300, 200);
        Session session = Session.register(0L, coverImage, SessionType.PAID, 10000L);
        session.close(); // <-- 도메인 메서드로 상태 변경

        NsUser user = NsUserTest.JAVAJIGI;
        Payment payment = new Payment("0", session.getId(), user.getId(), 10000L);
        LocalDateTime now = LocalDateTime.now();

        // when & then
        Assertions.assertThatThrownBy(() -> session.enroll(user, payment.getAmount(), now))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("모집 중이 아닙니다");
    }

}