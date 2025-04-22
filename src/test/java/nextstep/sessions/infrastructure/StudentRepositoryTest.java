package nextstep.sessions.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.sessions.domain.Student;
import nextstep.sessions.domain.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class StudentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Student student = new Student(1L, 1L);
        int savedId = studentRepository.save(student);

        assertThat(savedId).isEqualTo(1);

        Student savedStudent = studentRepository.findById(1L).orElse(null);
        assertThat(student).isEqualTo(savedStudent);
    }
}
