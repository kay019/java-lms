package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CourseTest {
    public static final Course C1 = new Course("test-course-1", 1L);

    @DisplayName("Course 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Course(1L, "test-course", 3L, LocalDateTime.now(), LocalDateTime.now()));
    }

}
