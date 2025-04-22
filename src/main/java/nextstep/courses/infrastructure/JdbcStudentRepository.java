package nextstep.courses.infrastructure;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.repository.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository()
public class JdbcStudentRepository implements StudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Long userId, Long sessionId) {
        String sql = "insert into session_student (ns_user_id, session_id) " +
                "values(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, userId);
            ps.setLong(2, sessionId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Student> findAllBySessionId(Long sessionId) {
        String sql = "select u.user_id, u.name, u.email " +
                "from ns_user u " +
                "join session_student ss on u.id = ss.ns_user_id " +
                "where ss.session_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String userId = rs.getString("user_id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            return new Student(userId, name, email);
        }, sessionId);
    }
}
