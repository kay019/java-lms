package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {
  @Test
  void 코스생성_생성자_테스트() {
    LocalDateTime now = LocalDateTime.now();
    Course course = new Course(1L, "title", 1L, now, now);
    String courseString = course.toString();

    assertEquals("Course{id=1, title='title', creatorId=1, createdAt=" + now + ", updatedAt=" + now + '}', courseString);
  }
}