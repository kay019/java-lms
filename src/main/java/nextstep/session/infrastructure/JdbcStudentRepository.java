package nextstep.session.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import nextstep.session.domain.Session;
import nextstep.session.domain.Student;
import nextstep.session.domain.StudentRepository;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStudentRepository implements StudentRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String sql = "INSERT INTO student(ns_user_id, session_id, create_dt) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, student.getUserId(), student.getSessionId(), LocalDateTime.now());
    }

    @Override
    public Optional<Student> findById(Long studentId) {
        String sql = "SELECT id, ns_user_id, session_id, create_dt FROM student WHERE id = ?";
        RowMapper<Student> rowMapper = (rs, rowNum) -> new Student(
                rs.getLong(1),
                new NsUser(rs.getLong(2)),
                new Session(rs.getLong(3)),
                toLocalDateTime(rs.getTimestamp(4)));

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, studentId));
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
