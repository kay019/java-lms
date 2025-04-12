package nextstep.session.domain;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseTest;
import nextstep.session.domain.image.ImageTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionTest {
    public static final Session S1 = new Session(1L, CourseTest.C1, ImageTest.I1, LocalDateTime.now(), LocalDateTime.now());

    @DisplayName("Session 인스턴스 생성")
    @Test
    public void testConstructor() {
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> new Session(1L, new Course(), ImageTest.I1, now, now, now, now));
    }

    @DisplayName("Course 등록")
    @Test
    public void testToCourse() {
        Session session = new Session(1L, new Course(), ImageTest.I1, LocalDateTime.now(), LocalDateTime.now());
        session.toCourse(CourseTest.C1);
        assertThat(session).isEqualTo(new Session(1L, CourseTest.C1, ImageTest.I1, LocalDateTime.now(), LocalDateTime.now()));
    }
}
