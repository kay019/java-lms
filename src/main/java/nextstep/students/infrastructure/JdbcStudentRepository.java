package nextstep.students.infrastructure;

import nextstep.students.domain.Student;
import nextstep.students.domain.StudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("studentRepository")
public class JdbcStudentRepository implements StudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
        String sql = "insert into student (name, email, budget) values(?, ?, ?)";
        return jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getBudget());
    }

    @Override
    public Student findById(Long id) {
        String sql = "select id, name, email, budget from student where id = ?";
        RowMapper<Student> rowMapper = (rs, rowNum) -> new Student(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getLong(4));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
