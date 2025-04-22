package nextstep.courses.repository;

import nextstep.courses.entity.CourseEntity;

public interface CourseRepository {
  long save(CourseEntity course);
  CourseEntity findById(Long id);
}
