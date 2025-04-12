package nextstep.session.domain;

import nextstep.sessionstudent.SessionStudent;
import nextstep.sessionstudent.SessionStudentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    void 강의_수강신청은_모집_상태가_모집중일_때만_가능하다() {
        long sessionFee = 20000;
        int maxStudentCount = 1;

        Session session = new SessionBuilder()
                .paid(sessionFee, maxStudentCount)
                .recruitmentStatus(RecruitmentStatus.NOT_RECRUITING)
                .build();

        IllegalStateException e = assertThrows(IllegalStateException.class,
            () -> session.register(NsUserTest.JAVAJIGI, new Money(sessionFee), 0));

        assertThat(e).hasMessage("수강신청이 불가능한 상태입니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"false,APPROVED", "true,PENDING"})
    void register_메서드는_selectionRequired_여부에_따라_SessionStudentStatus를_결정한다(
            boolean selectionRequired, SessionStudentStatus sessionStudentStatus) {
        long sessionFee = 20000;
        int maxStudentCount = 1;
        NsUser loginUser = NsUserTest.JAVAJIGI;

        Session session = new SessionBuilder()
                .paid(sessionFee, maxStudentCount)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .selectionRequired(selectionRequired)
                .build();

        SessionStudent student = session.register(loginUser, new Money(sessionFee), 0);

        assertThat(student.getSessionId()).isEqualTo(session.getId());
        assertThat(student.getNsUserId()).isEqualTo(loginUser.getId());
        assertThat(student.getStatus()).isEqualTo(sessionStudentStatus);
    }

}
