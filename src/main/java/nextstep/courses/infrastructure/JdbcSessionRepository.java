package nextstep.courses.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Participant;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.springframework.jdbc.core.JdbcTemplate;


public class JdbcSessionRepository implements SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_SESSION_WITH_PARTICIPANTS =
        "SELECT s.*, i.url, i.width, i.height, i.format, i.size, p.user_id " +
        "FROM session s " +
        "JOIN image i ON s.image_id = i.id " +
        "LEFT JOIN participant p ON s.id = p.session_id " +
        "WHERE s.id = ?";

    private enum SessionType {
        FREE,
        PAID
    }


    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        Session session = jdbcTemplate.query(
            SELECT_SESSION_WITH_PARTICIPANTS,
            this::extractSessionWithParticipants,
            id
        );
        return Optional.ofNullable(session);
    }

    private Session extractSessionWithParticipants(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }

        Session session = mapToSession(rs);
        addParticipantIfExists(session, rs);

        while (rs.next()) {
            addParticipantIfExists(session, rs);
        }

        return session;
    }

    private void addParticipantIfExists(Session session, ResultSet rs) throws SQLException {
        long userId = rs.getLong("user_id");
        if (!rs.wasNull()) {
            session.addParticipant(new Participant(userId));
        }
    }

    private Session mapToSession(ResultSet rs) throws SQLException {
        Image image = new Image(
            rs.getString("url"),
            rs.getInt("width"),
            rs.getInt("height"),
            rs.getString("format"),
            rs.getInt("size")
        );

        Period period = new Period(
            rs.getTimestamp("start_at").toLocalDateTime().toLocalDate(),
            rs.getTimestamp("end_at").toLocalDateTime().toLocalDate()
        );

        SessionType sessionType = SessionType.valueOf(rs.getString("session_type"));
        if (SessionType.FREE.equals(sessionType)) {
            return new FreeSession(image, period);
        }
        if (SessionType.PAID.equals(sessionType)) {
            return new PaidSession(
                image,
                period,
                rs.getInt("max_participants"),
                rs.getLong("price")
            );
        }
        throw new IllegalArgumentException("Unknown session type: " + sessionType);
    }
}
