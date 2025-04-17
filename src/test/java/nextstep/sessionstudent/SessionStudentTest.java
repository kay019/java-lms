package nextstep.sessionstudent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class SessionStudentTest {

    @Test
    void 승인_거절시_상태는_pending_이여야_한다() {
        SessionStudent sessionStudent = new SessionStudent(23921L, 219L, false);

        IllegalStateException e = catchThrowableOfType(
            sessionStudent::cancel,
            IllegalStateException.class
        );

        assertThat(e).hasMessage("세션 수강신청이 대기 상태가 아닙니다.");
    }

}
