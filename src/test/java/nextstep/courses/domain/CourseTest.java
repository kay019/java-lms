package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {
  @Test
  void 코스에_강의추가(){
    Course course = new Course("백엔드 과정", 1L);
    Session session = new Session(course, "유료강의", LocalDateTime.now(), LocalDateTime.now().plusDays(30), new FreeEnrollmentPolicy());
    course.addSession(session);

    assertEquals(1, course.getSessions().size());
  }
}