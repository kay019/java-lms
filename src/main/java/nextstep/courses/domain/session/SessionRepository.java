package nextstep.courses.domain.session;

import java.util.List;

public interface SessionRepository {
    int save(Session session);

    Session findById(Long sessionId);

    List<Session> findByCourse(Long courseId);
}
