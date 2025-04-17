package nextstep.sessionstudent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.*;

class SessionStudentTest {

    @Test
    void 승인_거절시_상태는_pending_이여야_한다() {
        SessionStudent sessionStudent = new SessionStudent(23921L, 219L, false);

        IllegalStateException e = catchThrowableOfType(
            () -> sessionStudent.cancelled(new ApprovalContext(true, true)),
            IllegalStateException.class
        );

        assertThat(e).hasMessage("세션 수강신청이 대기 상태가 아닙니다.");
    }

    @Test
    void 승인_거절시_선발_가능한_세션이_아니면_예외가_발생한다() {
        SessionStudent sessionStudent = new SessionStudent(23921L, 219L, true);

        IllegalStateException e = catchThrowableOfType(
            () -> sessionStudent.approved(new ApprovalContext(false, true)),
            IllegalStateException.class
        );

        assertThat(e).hasMessage("선발 가능한 세션이 아닙니다.");
    }

    @Test
    void 승인_거절시_Cousre_owner가_아니라면_예외가_발생한다() {
        SessionStudent sessionStudent = new SessionStudent(23921L, 219L, true);

        IllegalStateException e = catchThrowableOfType(
            () -> sessionStudent.cancelled(new ApprovalContext(true, false)),
            IllegalStateException.class
        );

        assertThat(e).hasMessage("세션 수강신청을 승인/거절할 권한이 없습니다.");
    }

}
