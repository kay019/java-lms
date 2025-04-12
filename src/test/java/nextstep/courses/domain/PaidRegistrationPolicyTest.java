package nextstep.courses.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class PaidRegistrationPolicyTest {

    @Test
    void 유료_강의는_강의_최대_수강_인원을_초과할_수_없다() {
        long sessionFee = 20000;
        int maxStudentCount = 1;

        Session session = new SessionBuilder()
                .paid(sessionFee, maxStudentCount)
                .sessionStatus(SessionStatus.RECRUITING)
                .build();

        IllegalArgumentException e = catchIllegalArgumentException(() -> {
            session.register(NsUserTest.JAVAJIGI, new Money(sessionFee));
            session.register(NsUserTest.SANJIGI, new Money(sessionFee));
        });

        assertThat(e).hasMessage("강의 최대 수강 인원을 초과할 수 없습니다.");
    }

    @Test
    void 유료_강의는_수강생이_결제한_금액과_수강료가_일치할_때_수강_신청이_가능하다() {
        long sessionFee = 20000;
        int maxStudentCount = 1;

        Session session = new SessionBuilder()
                .paid(sessionFee, maxStudentCount)
                .sessionStatus(SessionStatus.RECRUITING)
                .build();

        IllegalArgumentException e = catchIllegalArgumentException(() -> {
            session.register(NsUserTest.JAVAJIGI, new Money(sessionFee - 1));
        });

        assertThat(e).hasMessage("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
    }

}
