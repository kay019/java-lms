package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.session.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    @Override
    public Session findById(Long sessionId) {
        return null;
    }

    @Override
    public List<Session> findByCourse(Long courseId) {
        return null;
    }
}
