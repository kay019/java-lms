package nextstep.courses.infrastructure;

import nextstep.courses.InvalidNaturalNumberException;
import nextstep.courses.PayStrategyFactory;
import nextstep.courses.domain.*;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;
    private UserRepository userRepository;
    private final PayStrategyFactory payStrategyFactory;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, UserRepository userRepository, PayStrategyFactory payStrategyFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.payStrategyFactory = payStrategyFactory;
    }

    @Override
    public int save(Session session) {
        // session 저장
        String sql_session = "insert into session (start_at, end_at, image_size, image_type, image_width, image_height, price) values(?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql_session, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(session.getStartDate()));
            ps.setTimestamp(2, Timestamp.valueOf(session.getEndDate()));
            ps.setLong(3, session.getImageSize());
            ps.setString(4, session.getImageType());
            ps.setLong(5, session.getImageWidth());
            ps.setLong(6, session.getImageHeight());
            ps.setLong(7, session.getPrice()); // 가격 필드가 있다고 가정
            return ps;
        }, generatedKeyHolder);

        Long sessionId = generatedKeyHolder.getKey().longValue();
        Registry registry = session.getRegistry();

        // registry 저장
        String sql_registry = "insert into registry (session_id, session_state, pay_strategy, capacity) values(?, ?, ?, ?)";

        jdbcTemplate.update(sql_registry, sessionId, registry.getSessionState(), registry.getPayStrategy(), registry.getCapacity());

        List<NsStudent> students = registry.getStudents();

        // students 저장
        String sql_students = "insert into ns_students (session_id, user_id) values(?, ?)";
        for(NsStudent student: students) {
            jdbcTemplate.update(sql_students, sessionId, student.getUserId());
        }

        return 1;
    }

    @Override
    public Session findById(Long id) {
        // students 찾기
        String sql_students = "SELECT user_id FROM ns_students WHERE session_id = ?";
        List<NsStudent> students = jdbcTemplate.query(sql_students, (rs, rowNum) ->
                new NsStudent(
                        userRepository.findById(rs.getLong(1))
                                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저를 찾을 수 없습니다."))
                        , id
                ), id);

        // registry 찾기
        String sql_repository = "SELECT pay_strategy, session_state, capacity FROM registry WHERE session_id = ?";
        Registry registry = jdbcTemplate.queryForObject(sql_repository, (rs, rowNum) ->
                new Registry(
                        students,
                        payStrategyFactory.getStrategy(rs.getString(1)),
                        rs.getString(2),
                        rs.getLong(3)
                ), id);

        // session 찾기
        String sql_session = "SELECT id, start_at, end_at, image_size, image_type, image_width, image_height, price FROM session WHERE id = ?";
        return jdbcTemplate.queryForObject(sql_session, (rs, rowNum) ->
                new Session(
                        rs.getLong(1),
                        toLocalDateTime(rs.getTimestamp(2)),
                        toLocalDateTime(rs.getTimestamp(3)),
                        registry,
                        new Image(
                                rs.getLong(4),
                                rs.getString(5),
                                rs.getLong(6),
                                rs.getLong(7)
                        ),
                        rs.getLong(8)
                ),id);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
