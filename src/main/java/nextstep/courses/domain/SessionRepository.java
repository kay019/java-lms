package nextstep.courses.domain;

public interface SessionRepository {

  public int save(Session session);

  public Session findById(Long sessionId);

}
