package nextstep.courses.domain;

import nextstep.courses.RegistryDto;
import nextstep.courses.SessionDto;

import java.util.List;

public interface SessionRepository {
    int save(Session session);

    // Session findById(Long id);
    SessionDto findSessionDtoById(Long id);
    RegistryDto findRegistryDtoBySessionId(Long sessionId);
    List<Long> findStudentIdBySessionId(Long sessionId);
}
