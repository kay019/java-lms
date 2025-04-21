package nextstep.sessions.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void createTest() {
        Student student = new Student(1L, 1L);
        assertThat(student).isEqualTo(new Student(1L, 1L));
    }

    @Test
    void isSameUserTest() {
        Student student = new Student(1L, 1L);
        
        assertThat(student.isSameUser(1L)).isTrue();
        assertThat(student.isSameUser(2L)).isFalse();
    }
}
