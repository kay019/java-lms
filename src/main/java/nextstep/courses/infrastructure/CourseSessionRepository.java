package nextstep.courses.infrastructure;

import nextstep.courses.domain.CourseSession;

import java.util.Optional;

public interface CourseSessionRepository {

    Optional<CourseSession> findById(final Long id);
} 
