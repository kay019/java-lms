package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CourseTest {

    @DisplayName("Course 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Course(1L, "test-course", 3L, LocalDateTime.now(), LocalDateTime.now()));
    }
}
