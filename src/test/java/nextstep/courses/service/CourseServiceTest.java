package nextstep.courses.service;

import nextstep.courses.domain.Cohort;
import nextstep.courses.domain.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CourseServiceTest {
  @Autowired
  private CourseService courseService;

  @Test
  void crud() {
    Cohort cohort = new Cohort(2L, "1기");
    Course course = new Course("자바뿌셔", 1L, cohort);

    long courseId = courseService.save(course);
    Course savedCourse = courseService.findById(courseId);

    assertThat(savedCourse.id()).isEqualTo(courseId);
    assertThat(savedCourse.cohort()).isEqualTo(cohort);
  }
}
