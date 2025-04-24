package nextstep.courses.infrastructure;

import nextstep.courses.PayStrategyFactory;
import nextstep.courses.domain.*;
import nextstep.users.domain.NsStudent;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;
    private final PayStrategyFactory payStrategyFactory;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, PayStrategyFactory payStrategyFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.payStrategyFactory = payStrategyFactory;
    }

    @Override
    public int save(Session session) {
        // session 저장
        String sql_session = "insert into session (start_at, end_at, price, session_progress_state) values(?, ?, ?, ?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql_session, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(session.getStartDate()));
            ps.setTimestamp(2, Timestamp.valueOf(session.getEndDate()));
            ps.setLong(3, session.getPrice());
            ps.setString(4, session.getSessionState().name());
            return ps;
        }, generatedKeyHolder);

        Long sessionId = generatedKeyHolder.getKey().longValue();
        Registry registry = session.getRegistry();

        // images 저장
        List<Image> images = session.getCoverImages();
        String sql_images = "insert into images (session_id, image_size, image_type, image_width, image_height) values(?, ?, ?, ?, ?)";
        for(Image image : images) {
            jdbcTemplate.update(sql_images, sessionId, image.getSize(), image.getImageType(), image.getWidth(), image.getHeight());
        }

        // registry 저장
        String sql_registry = "insert into registry (session_id, session_state, pay_strategy, capacity, session_access_type) values(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql_registry, sessionId, registry.getSessionRecruitmentState(), registry.getPayStrategy(), registry.getCapacity(), registry.getSessionAccessType());

        // students 저장
        List<NsStudent> students = registry.getStudents();
        String sql_students = "insert into ns_students (session_id, user_id, application_state) values(?, ?, ?)";
        for(NsStudent student: students) {
            jdbcTemplate.update(sql_students, sessionId, student.getUserId(), student.getApplicationState());
        }

        return 1;
    }

    @Override
    public Session findById(Long id) {
        // students 찾기
        String sql_students = "SELECT user_id, application_state FROM ns_students WHERE session_id = ?";
        List<NsStudent> students = jdbcTemplate.query(sql_students, (rs, rowNum) ->
                new NsStudent(rs.getLong(1), id, rs.getString(2)), id);

        // registry 찾기
        String sql_repository = "SELECT pay_strategy, session_state, capacity, session_access_type FROM registry WHERE session_id = ?";

        Registry registry = jdbcTemplate.queryForObject(sql_repository, (rs, rowNum) ->
                new Registry(
                        students,
                        payStrategyFactory.getStrategy(rs.getString(1)),
                        SessionRecruitmentState.valueOf(rs.getString(2)),
                        new PositiveNumber(rs.getLong(3)),
                        SessionAccessType.valueOf(rs.getString(4))
                ), id);

        // images 찾기
        String sql_images = "SELECT image_size, image_type, image_width, image_height FROM images WHERE session_id = ?";
        List<Image> images = jdbcTemplate.query(sql_images, (rs, rowNum) ->
                new Image(rs.getLong(1), rs.getString(2), rs.getLong(3), rs.getLong(4)), id);

        // session 찾기
        String sql_session = "SELECT id, start_at, end_at, price, session_progress_state FROM session WHERE id = ?";
        return jdbcTemplate.queryForObject(sql_session, (rs, rowNum) ->
                new Session(
                        rs.getLong(1),
                        toLocalDateTime(rs.getTimestamp(2)),
                        toLocalDateTime(rs.getTimestamp(3)),
                        registry,
                        images,
                        rs.getLong(4),
                        SessionProgressState.valueOf(rs.getString(5))
                ),id);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
