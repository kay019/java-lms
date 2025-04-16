package nextstep.users.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.courses.domain.PositiveNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NsStudentsTest {
    @DisplayName("이미 수강생인 사람은 또 수강생이 될 수 없음")
    @Test
    public void cannot_repeat_register() {
        NsStudents nsStudents = new NsStudents(new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L));
        assertThatThrownBy(() -> nsStudents.enroll(new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L), new PositiveNumber(2L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("이미 수강생입니다.");
    }

    @DisplayName("수강인원을 초과하는 인원은 수강생이 될 수 없음")
    @Test
    public void cannot_over_capacity() {
        NsStudents nsStudents = new NsStudents(new NsStudent(NsUserTest.JAVAJIGI.getId(), 0L));
        assertThatThrownBy(() -> nsStudents.enroll(new NsStudent(NsUserTest.SANJIGI.getId(), 0L), new PositiveNumber(1L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("최대 수강 인원을 초과하였습니다.");
    }
}
