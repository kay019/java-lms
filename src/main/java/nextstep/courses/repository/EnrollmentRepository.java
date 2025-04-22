package nextstep.courses.repository;

import nextstep.courses.entity.EnrollmentEntity;

import java.util.List;

public interface EnrollmentRepository {
  long save(EnrollmentEntity enrollment);
  void saveAll(List<EnrollmentEntity> enrollments);
  List<EnrollmentEntity> findBySessionId(Long sessionId);
}
