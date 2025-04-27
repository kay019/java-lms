package nextstep.session.domain;

import java.util.Optional;

public interface SessionRepository {
    Long save(Session session);

    Optional<Session> findById(Long sessionId);
}
