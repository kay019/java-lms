package nextstep.courses.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @ParameterizedTest
    @EnumSource(value = SessionStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "RECRUITING")
    void 강의_수강신청은_강의_상태가_모집중일_때만_가능하다(SessionStatus sessionStatus) {
        int maxStudentCount = 1;
        int sessionFee = 20000;

        CoverImage coverImage = CoverImageTest.createCoverImage1();
        LocalDateTime startedAt = LocalDateTime.of(2023, 10, 1, 0, 0, 0);
        LocalDateTime endedAt = LocalDateTime.of(2023, 10, 1, 23, 0, 0);

        Session session
            = SessionFactory.ofPaid(1, coverImage, sessionStatus, sessionFee, maxStudentCount, startedAt, endedAt);

        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> session.register(NsUserTest.JAVAJIGI, new Money(sessionFee)));

        assertThat(e).hasMessage("수강신청이 불가능한 상태입니다.");
    }

    @Test
    void register하면_Payment객체를_응답한다() {
        int maxStudentCount = 1;
        int sessionFee = 20000;
        NsUser loginUser = NsUserTest.JAVAJIGI;

        CoverImage coverImage = CoverImageTest.createCoverImage1();
        LocalDateTime startedAt = LocalDateTime.of(2023, 10, 1, 0, 0, 0);
        LocalDateTime endedAt = LocalDateTime.of(2023, 10, 1, 23, 0, 0);

        Session session
            = SessionFactory.ofPaid(1, coverImage, SessionStatus.RECRUITING, sessionFee, maxStudentCount, startedAt, endedAt);

        Payment payment = session.register(loginUser, new Money(sessionFee));

        assertThat(payment.getSession()).isEqualTo(session);
        assertThat(payment.getNsUser()).isEqualTo(loginUser);
        assertThat(payment.getAmount()).isEqualTo(new Money(sessionFee));
    }



}
