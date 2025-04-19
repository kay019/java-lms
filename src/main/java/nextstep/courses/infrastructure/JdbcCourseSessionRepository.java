package nextstep.courses.infrastructure;

import nextstep.courses.domain.CourseSession;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcCourseSessionRepository implements CourseSessionRepository {

    @Override
    public Optional<CourseSession> findById(final Long id) {
        return Optional.empty();
    }
}
