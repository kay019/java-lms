package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseTest {
    
    @Test
    @DisplayName("코스 생성 테스트")
    public void createCourse() {
        // given
        Long id = 1L;
        String title = "Java Programming";
        Long creatorId = 100L;
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2023, 1, 1, 10, 0);
        
        // when
        Course course = new Course(id, title, creatorId, createdAt, updatedAt);
        
        // then
        assertThat(course.getId()).isEqualTo(id);
        assertThat(course.getTitle()).isEqualTo(title);
        assertThat(course.getCreatorId()).isEqualTo(creatorId);
        assertThat(course.getCreatedAt()).isEqualTo(createdAt);
    }
} 
