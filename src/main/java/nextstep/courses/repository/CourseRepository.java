package nextstep.courses.repository;

import nextstep.courses.entity.CohortEntity;
import nextstep.courses.entity.CourseEntity;

public interface CourseRepository {
  long save(CourseEntity course);
  CourseEntity findById(Long id);
  long saveCohort(CohortEntity cohort);
  CohortEntity findCohortByCourseId(Long courseId);
}
