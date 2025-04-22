package nextstep.courses.domain.repository;

import nextstep.courses.domain.Session;

import java.util.Optional;

public interface SessionRepository {
    Long save(Session session);

    Optional<Session> findById(Long id);
}
