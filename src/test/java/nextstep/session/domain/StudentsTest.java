package nextstep.session.domain;

import static nextstep.session.SessionTest.createSession;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentsTest {
    @Test
    void createTest() {
        Student student = new Student(JAVAJIGI, new Session(1L, null, null, null, null, null, null, null, List.of()));

        Students students = new Students(2L, List.of(student));

        assertThat(students).isEqualTo(new Students(2L, List.of(student)));
    }

    @DisplayName("유료 강의는 강의 최대 수강 인원을 초과할 수 없다.")
    @Test
    void enrollCapacityExceptionTest() {
        Student student = new Student(JAVAJIGI, createSession());

        Students students = new Students(1L, List.of(student));

        assertThatThrownBy(() -> students.enroll(new NsUser())).isInstanceOf(IllegalStateException.class)
                .hasMessage("capacity is full");
    }

    @Test
    void enrollAlreadyEnrolledExceptionTest() {
        Student student = new Student(JAVAJIGI, createSession());

        Students students = new Students(2L, List.of(student));

        assertThatThrownBy(() -> students.enroll(JAVAJIGI))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("already enrolled student");
    }

}
