package nextstep.courses.infrastructure;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.repository.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionStudentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long userId, Long sessionId) {
        String sql = "insert into session_student (ns_user_id, session_id) " +
                "values(?, ?)";
        return jdbcTemplate.update(sql,
                userId,
                sessionId);
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
