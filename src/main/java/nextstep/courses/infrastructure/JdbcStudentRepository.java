package nextstep.courses.infrastructure;

import nextstep.courses.domain.EnrollStatus;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String sql = "insert into student (name, email, budget, enroll_status) values(?, ?, ?, ?)";
        int res = jdbcTemplate.update(sql,
                student.getName(),
                student.getEmail(),
                student.getBudget(),
                student.getEnrollStatus().name()
        );
        saveSessions(student.getId(), student.getSessionIds());
        return res;
    }

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "select * from student where id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(mapRowToStudent(rs));
        }, id);
    }

    private void saveSessions(Long studentId, List<Long> sessionIds) {
        String sql = "INSERT INTO session_student (student_id, session_id) VALUES (?, ?)";
        for (Long sessionId : sessionIds) {
            jdbcTemplate.update(sql, studentId, sessionId);
        }
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        Long budget = rs.getLong("budget");
        EnrollStatus enrollStatus = EnrollStatus.valueOf(rs.getString("enroll_status"));

        return new Student(id, name, email, budget, enrollStatus);
    }
}
