package nextstep.courses.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.Participant;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.ParticipantRepository;
import org.springframework.jdbc.core.JdbcTemplate;


public class JdbcSessionRepository implements SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ImageRepository imageRepository;
    private final ParticipantRepository participantRepository;

    private enum SessionType {
        FREE,
        PAID
    }


    public JdbcSessionRepository(JdbcTemplate jdbcTemplate, ImageRepository imageRepository, ParticipantRepository participantRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.imageRepository = imageRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public Optional<Session> findById(Long id) {
        Session session = jdbcTemplate.queryForObject("SELECT * FROM session WHERE id = ?", (rs, rowNum) -> {
            SessionType sessionType = SessionType.valueOf(rs.getString("session_type"));
            Period period = new Period(rs.getTimestamp("start_at").toLocalDateTime().toLocalDate(), rs.getTimestamp("end_at").toLocalDateTime().toLocalDate());
            Image image = imageRepository.findById(rs.getLong("image_id")).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
            List<Participant> participants = participantRepository.findBySessionId(rs.getLong("id"));

            if (sessionType == SessionType.FREE) {
                return new FreeSession(id, image, period, participants);
            } else {
                return new PaidSession(id, image, period, participants, rs.getInt("max_participants"), rs.getLong("price"));
            }
        }, id);

        return Optional.of(session);
    }
}
