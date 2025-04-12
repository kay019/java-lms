package nextstep.session.domain;

public interface SessionRepository {
    int save(Session session);
    Session findById(long id);
}
