package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.entity.CohortEntity;
import nextstep.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("courseService")
@Transactional
public class CourseService {

  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public long save(Course course) {
    long courseId = courseRepository.save(course.toCourseEntity());
    CohortEntity cohortEntity = course.cohort().toCohortEntity(courseId);
    courseRepository.saveCohort(cohortEntity);
    return courseId;
  }

  public Course findById(Long id) {
    CohortEntity cohortEntity = courseRepository.findCohortByCourseId(id);
    return courseRepository.findById(id).toCourse(cohortEntity.toCohort());
  }
}
