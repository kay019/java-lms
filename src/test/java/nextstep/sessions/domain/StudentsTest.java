package nextstep.sessions.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

class StudentsTest {
    @Test
    void createTest() {
        Student student = new Student(1L, 1L);

        Students students = new Students(2L, List.of(student));

        assertThat(students).isEqualTo(new Students(2L, List.of(student)));
    }

    @Test
    void enrollCapacityExceptionTest() {
        Student student = new Student(1L, 1L);

        Students students = new Students(1L, List.of(student));

        assertThatThrownBy(() -> students.enroll(new NsUser())).isInstanceOf(IllegalStateException.class)
                .hasMessage("capacity is full");
    }

    @Test
    void enrollAlreadyEnrolledExceptionTest() {
        Student student = new Student(1L, 1L);

        Students students = new Students(2L, List.of(student));

        assertThatThrownBy(() -> students.enroll(new NsUser(1L, "userId", "password", "name", "email")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("already enrolled student");
    }
}
