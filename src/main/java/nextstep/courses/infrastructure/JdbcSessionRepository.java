package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.Participant;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.ParticipantRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;



public class JdbcSessionRepository implements SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ImageRepository imageRepository;
    private final ParticipantRepository participantRepository;

    private enum SessionType {
        FREE,
        PAID;

        public static SessionType from(Class<? extends Session> sessionClass) {
            if (sessionClass == FreeSession.class) {
                return FREE;
            } else if (sessionClass == PaidSession.class) {
                return PAID;
            }
            throw new IllegalArgumentException("Invalid session type");
        }
    }


    public JdbcSessionRepository(JdbcTemplate jdbcTemplate, ImageRepository imageRepository, ParticipantRepository participantRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.imageRepository = imageRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public Session save(Session session) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            Image image = imageRepository.save(session.getCoverImage());
            participantRepository.saveAll(session.getId(), session.getParticipants());

            PreparedStatement ps = connection.prepareStatement("INSERT INTO session (session_type, image_id, start_at, end_at, price, max_participants, status) VALUES (?, ?, ?, ?, ?, ?, ?)", new String[] {"id"});
            ps.setString(1, SessionType.from(session.getClass()).name());
            ps.setLong(2, image.getId());
            ps.setTimestamp(3, Timestamp.valueOf(session.getPeriod().getStartDate().atStartOfDay()));
            ps.setTimestamp(4, Timestamp.valueOf(session.getPeriod().getEndDate().atStartOfDay()));
            if (session instanceof PaidSession) {
                ps.setLong(5, ((PaidSession) session).getPrice());
                ps.setLong(6, ((PaidSession) session).getMaxParticipants());
            } else {
                ps.setNull(5, Types.BIGINT);
                ps.setNull(6, Types.BIGINT);
            }
            ps.setString(7, session.getStatus().name());
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue()).orElseThrow(() -> new IllegalArgumentException("세션을 찾을 수 없습니다."));
    }

    @Override
    public Optional<Session> findById(Long id) {
        Session session = jdbcTemplate.queryForObject("SELECT * FROM session WHERE id = ?", (rs, rowNum) -> {
            SessionType sessionType = SessionType.valueOf(rs.getString("session_type"));
            Period period = new Period(rs.getTimestamp("start_at").toLocalDateTime().toLocalDate(), rs.getTimestamp("end_at").toLocalDateTime().toLocalDate());
            Image image = imageRepository.findById(rs.getLong("image_id")).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
            List<Participant> participants = participantRepository.findBySessionId(rs.getLong("id"));

            if (sessionType == SessionType.FREE) {
                return new FreeSession(id, image, period, participants, SessionStatus.valueOf(rs.getString("status")));
            } else {
                return new PaidSession(id, image, period, participants, SessionStatus.valueOf(rs.getString("status")), rs.getInt("max_participants"), rs.getLong("price"));
            }
        }, id);

        return Optional.of(session);
    }
}
