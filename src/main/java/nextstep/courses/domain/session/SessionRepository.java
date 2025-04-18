package nextstep.courses.domain.session;

import nextstep.courses.entity.SessionEntity;

public interface SessionRepository {
    Long save(SessionEntity sessionEntity);

    SessionEntity findById(Long id);
}
