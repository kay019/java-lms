package nextstep.courses.repository;

import nextstep.courses.entity.CohortEntity;

public interface CohortRepository {
  long saveCohort(CohortEntity cohort);
  CohortEntity findByCourseId(Long courseId);
}
