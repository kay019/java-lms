package nextstep.courses.domain;

public interface ParticipantRepository {
    int save(Long sessionId, Participant participant);

}
