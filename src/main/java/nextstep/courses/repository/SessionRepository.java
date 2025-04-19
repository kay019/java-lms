package nextstep.courses.repository;

import nextstep.courses.entity.SessionEntity;

public interface SessionRepository {
  long save(SessionEntity session);
  SessionEntity findById(Long id);
}
