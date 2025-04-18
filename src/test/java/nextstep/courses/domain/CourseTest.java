package nextstep.courses.domain;

import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.SessionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @DisplayName("Course 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Course(1L, "test-course", 3L, LocalDateTime.now(), LocalDateTime.now()));
    }

    @DisplayName("CourseEntity로부터 Course 인스턴스 생성")
    @Test
    public void testCreateCourse() throws IOException {
        List<SessionEntity> sessionEntities = Collections.singletonList(
            SessionEntity.builder()
                .id(1L)
                .fee(1000L)
                .capacity(30)
                .imageUrl("http://example.com/image.jpg")
                .imageType("JPEG")
                .startDate(LocalDateTime.of(2023, 1, 1, 9, 0))
                .endDate(LocalDateTime.of(2023, 12, 31, 18, 0))
                .type("FREE")
                .status("ENROLLING")
                .build()
        );

        CourseEntity courseEntity = CourseEntity.builder()
            .id(1L)
            .title("Sample Course")
            .creatorId(123L)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .deleted(false)
            .build();

        Course course = Course.from(courseEntity, sessionEntities);

        assertNotNull(course);
    }
}
