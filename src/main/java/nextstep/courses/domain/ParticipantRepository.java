package nextstep.courses.domain;

import java.util.List;

public interface ParticipantRepository {
    int save(Long sessionId, Participant participant);

    void saveAll(Long sessionId, List<Participant> participants);

    List<Participant> findBySessionId(Long sessionId);

}
