package nextstep.users.domain;

import nextstep.courses.domain.ApplicationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NsStudentTest {
    @DisplayName("학생 정상 생성")
    @Test
    public void create_student() {
        assertThatNoException().isThrownBy(() ->
                new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L)
        );
    }

    @DisplayName("학생 생성한 유저가 동일한지 비교")
    @Test
    public void compare_student_user() {
        assertThat(new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L).isSameUser(NsUserTest.JAVAJIGI)).isTrue();
    }

    @DisplayName("수강 신청 상태가 pending일 때 수락/거절 가능")
    @Test
    public void approve_student() {
        NsStudent student = new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L);
        student.changeStatus(ApplicationState.APPROVED);
        assertThat(student.getApplicationState()).isEqualTo("APPROVED");
    }

    @DisplayName("수강 신청 상태가 pending이 아닐 때에는 상태 변결 불가능")
    @Test
    public void can_not_approve_student() {
        NsStudent student = new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L, ApplicationState.APPROVED);
        assertThatThrownBy(() -> student.changeStatus(ApplicationState.REJECTED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 수강 상태가 결정된 학생입니다.");
    }
}
