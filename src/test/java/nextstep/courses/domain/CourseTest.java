package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    @DisplayName("과정 생성")
    void createCourse() {
        Course course = new Course("과정1", 1L);
        assertThat(course).isNotNull();
    }

    @Test
    @DisplayName("세션 추가")
    void addSession() {
        Course course = new Course("과정1", 1L);
        Session session = SessionTest.DEFAULT_SESSION;
        course.addSession(session);
        assertThat(course.getSessionByTerm(1)).isEqualTo(session);
    }
}
