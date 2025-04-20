package nextstep.courses.infrastructure;

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
        return jdbcTemplate.update("INSERT INTO participant (session_id, user_id) VALUES (?, ?)", sessionId, participant.getId());
    }
}
