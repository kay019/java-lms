package nextstep.session.domain;

import static nextstep.session.SessionTest.createSession;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static nextstep.users.domain.NsUserTest.SANJIGI;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void createTest() {
        Student student = new Student(JAVAJIGI, createSession());
        assertThat(student).isEqualTo(new Student(JAVAJIGI, createSession()));
    }

    @Test
    void isSameUserTest() {
        Student student = new Student(JAVAJIGI, createSession());

        assertThat(student.isSameUser(JAVAJIGI)).isTrue();
        assertThat(student.isSameUser(SANJIGI)).isFalse();
    }
}
