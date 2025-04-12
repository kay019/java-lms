package nextstep.session.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @ParameterizedTest
    @EnumSource(value = SessionStatus.class, mode = EnumSource.Mode.EXCLUDE, names = "RECRUITING")
    void 강의_수강신청은_강의_상태가_모집중일_때만_가능하다(SessionStatus sessionStatus) {
        long sessionFee = 20000;
        int maxStudentCount = 1;

        Session session = new SessionBuilder()
                .paid(sessionFee, maxStudentCount)
                .sessionStatus(sessionStatus)
                .build();

        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> session.register(NsUserTest.JAVAJIGI, new Money(sessionFee), 0));

        assertThat(e).hasMessage("수강신청이 불가능한 상태입니다.");
    }

    @Test
    void register하면_Payment객체를_응답한다() {
        long sessionFee = 20000;
        int maxStudentCount = 1;
        NsUser loginUser = NsUserTest.JAVAJIGI;

        Session session = new SessionBuilder()
                .paid(sessionFee, maxStudentCount)
                .sessionStatus(SessionStatus.RECRUITING)
                .build();

        Payment payment = session.register(loginUser, new Money(sessionFee), 0);

        assertThat(payment.getSessionId()).isEqualTo(session.getId());
        assertThat(payment.getNsUserId()).isEqualTo(loginUser.getId());
        assertThat(payment.getAmount()).isEqualTo(new Money(sessionFee));
    }



}
