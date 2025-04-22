package nextstep.courses.domain;

import java.util.List;

public interface ParticipantRepository {
    int save(Long sessionId, Participant participant);

    List<Participant> findBySessionId(Long sessionId);

}
