package nextstep.students.infrastructure;

import nextstep.courses.domain.Sessions;
import nextstep.students.domain.Student;
import nextstep.students.domain.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String sql = "insert into student (name, email, budget) values(?, ?, ?)";
        int res = jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getBudget());
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

    @Override
    public List<Student> findAllById(List<Long> studentIds) {
        String placeholders = studentIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        String sql = "SELECT * FROM student WHERE id IN (" + placeholders + ")";

        return jdbcTemplate.query(
                sql,
                studentIds.toArray(),
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getLong("budget")
                )
        );
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

        List<Long> sessionIds = jdbcTemplate.query(
                "SELECT session_id FROM session_student WHERE student_id = ?",
                (rs2, rowNum) -> rs2.getLong("session_id"),
                id
        );

        return new Student(id, name, email, budget, new Sessions(sessionIds));
    }
}
