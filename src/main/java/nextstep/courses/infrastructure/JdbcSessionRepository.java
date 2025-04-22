package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
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
import org.springframework.transaction.annotation.Transactional;

public class JdbcSessionRepository implements SessionRepository {
    private static final String INSERT_SESSION_SQL = 
        "INSERT INTO session (session_type, image_id, start_at, end_at, price, max_participants, status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_SESSION_SQL = 
        "SELECT * FROM session WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final ImageRepository imageRepository;
    private final ParticipantRepository participantRepository;

    public JdbcSessionRepository(JdbcTemplate jdbcTemplate, ImageRepository imageRepository, ParticipantRepository participantRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.imageRepository = imageRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    @Transactional
    public Session save(Session session) {
        Image savedImage = saveImage(session);
        saveParticipants(session);
        
        Long sessionId = saveSession(session, savedImage);
        return findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("세션을 찾을 수 없습니다."));
    }

    private Image saveImage(Session session) {
        return imageRepository.save(session.getCoverImage());
    }

    private void saveParticipants(Session session) {
        participantRepository.saveAll(session.getId(), session.getParticipants());
    }

    private Long saveSession(Session session, Image image) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SESSION_SQL, new String[] {"id"});
            setSessionParameters(ps, session, image);
            return ps;
        }, keyHolder);
        
        return keyHolder.getKey().longValue();
    }

    private void setSessionParameters(PreparedStatement ps, Session session, Image image) throws java.sql.SQLException {
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
    }

    @Override
    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            SELECT_SESSION_SQL,
            (rs, rowNum) -> mapToSession(rs),
            id
        ));
    }

    private Session mapToSession(ResultSet rs) throws java.sql.SQLException {
        SessionType sessionType = SessionType.from(rs.getString("session_type"));
        Period period = createPeriod(rs);
        Image image = getImage(rs);
        List<Participant> participants = getParticipants(rs);
        SessionStatus status = SessionStatus.from(rs.getString("status"));

        return createSession(rs.getLong("id"), sessionType, image, period, participants, status, rs);
    }

    private Period createPeriod(ResultSet rs) throws java.sql.SQLException {
        return new Period(
            rs.getTimestamp("start_at").toLocalDateTime().toLocalDate(),
            rs.getTimestamp("end_at").toLocalDateTime().toLocalDate()
        );
    }

    private Image getImage(ResultSet rs) throws java.sql.SQLException {
        return imageRepository.findById(rs.getLong("image_id"))
            .orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
    }

    private List<Participant> getParticipants(ResultSet rs) throws java.sql.SQLException {
        return participantRepository.findBySessionId(rs.getLong("id"));
    }

    private Session createSession(Long id, SessionType sessionType, Image image, Period period, 
                                List<Participant> participants, SessionStatus status, ResultSet rs) throws java.sql.SQLException {
        if (sessionType == SessionType.FREE) {
            return new FreeSession(id, image, period, participants, status);
        }
        if (sessionType == SessionType.PAID) {
            return new PaidSession(id, image, period, participants, status, 
                             rs.getInt("max_participants"), rs.getLong("price"));
        }
        throw new IllegalArgumentException("Invalid session type");
    }
}
