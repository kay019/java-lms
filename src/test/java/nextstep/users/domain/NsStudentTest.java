package nextstep.users.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class NsStudentTest {
    @DisplayName("학생 정상 생성")
    @Test
    public void create_student() {
        assertThatNoException().isThrownBy(() ->
                new NsStudent(NsUserTest.JAVAJIGI, 0L)
        );
    }

    @DisplayName("학생 생성한 유저가 동일한지 비교")
    @Test
    public void compare_student_user() {
        assertThat(new NsStudent(NsUserTest.JAVAJIGI, 0L).isSameUser(NsUserTest.JAVAJIGI)).isTrue();
    }
}
