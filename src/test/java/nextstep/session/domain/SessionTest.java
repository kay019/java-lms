package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {
    public static final Session S1 = new Session(1L, CourseTest.C1);

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Session(1L, new Course()));
    }

    @DisplayName("Course 등록")
    @Test
    public void testToCourse() {
        Session session = new Session(1L, new Course());
        session.toCourse(CourseTest.C1);
        assertThat(session).isEqualTo(new Session(1L, CourseTest.C1));
    }

}
