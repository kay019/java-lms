package nextstep.courses.service;

import nextstep.courses.domain.Course;
import nextstep.courses.entity.CohortEntity;
import nextstep.courses.repository.CohortRepository;
import nextstep.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("courseService")
@Transactional
public class CourseService {

  private final CourseRepository courseRepository;
  private final CohortRepository cohortRepository;

  public CourseService(CourseRepository courseRepository, CohortRepository cohortRepository) {
    this.courseRepository = courseRepository;
    this.cohortRepository = cohortRepository;
  }

  public long save(Course course) {
    long courseId = courseRepository.save(course.toCourseEntity());
    CohortEntity cohortEntity = course.cohort().toCohortEntity(courseId);
    cohortRepository.saveCohort(cohortEntity);
    return courseId;
  }

  public Course findById(Long id) {
    CohortEntity cohortEntity = cohortRepository.findByCourseId(id);
    return courseRepository.findById(id).toCourse(cohortEntity.toCohort());
  }
}
