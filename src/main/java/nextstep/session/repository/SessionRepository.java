package nextstep.session.repository;

import nextstep.session.domain.Session;

public interface SessionRepository {
    int save(Session session);
    Session findById(long id);
}
