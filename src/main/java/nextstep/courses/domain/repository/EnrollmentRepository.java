package nextstep.courses.domain.repository;

import nextstep.courses.domain.Enrollment;

import java.util.Optional;

public interface EnrollmentRepository {
    Long save(Enrollment enrollment);

    Optional<Enrollment> findById(Long sessionId, Long userId);
}
