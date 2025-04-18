package nextstep.courses.domain.session;

import nextstep.courses.entity.SessionEntity;

public interface SessionRepository {
    Long save(Session session, Long courseId);

    int saveAll(Sessions sessions, Long courseId);

    SessionEntity findById(Long id);
}
