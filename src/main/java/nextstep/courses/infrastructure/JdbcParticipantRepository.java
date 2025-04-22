package nextstep.courses.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.courses.domain.Participant;
import nextstep.courses.domain.ParticipantRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcParticipantRepository implements ParticipantRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcParticipantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long sessionId, Participant participant) {
        return jdbcTemplate.update("INSERT INTO participant (session_id, user_id) VALUES (?, ?)", sessionId, participant.getUserId());
    }

    @Override
    public void saveAll(Long sessionId, List<Participant> participants) {
        jdbcTemplate.batchUpdate("INSERT INTO participant (session_id, user_id) VALUES (?, ?)", participants.stream().map(participant -> new Object[] {sessionId, participant.getUserId()}).collect(Collectors.toList()));
    }

    @Override
    public List<Participant> findBySessionId(Long sessionId) {
        return jdbcTemplate.query("SELECT * FROM participant JOIN ns_user ON participant.user_id = ns_user.id WHERE session_id = ?",  (rs, rowNum) -> new Participant(rs.getLong("session_id"), rs.getLong("user_id")), sessionId);
    }
}
