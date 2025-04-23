package nextstep.courses.infrastructure;

import nextstep.courses.domain.Student;
import nextstep.courses.domain.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=always",
        "spring.sql.init.schema-locations=classpath:schema.sql",
        "spring.sql.init.data-locations="
})
class StudentRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentRepository studentRepository;

    @BeforeEach
    void setup() {
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
        try {
            jdbcTemplate.execute("DELETE FROM student");
            jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN id RESTART WITH 1");
            System.out.println("테이블 초기화 성공");
        } catch (Exception e) {
            System.err.println("테이블 초기화 실패: " + e.getMessage());
        }
    }

    @Test
    void 저장_테스트() {
        Student student = new Student("sh", "sh@nextstep.com", 1000L);
        int count = studentRepository.save(student);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void id_검색_테스트() {
        Student student = new Student("sh", "sh@nextstep.com", 1000L);
        studentRepository.save(student);
        Student savedStudent = studentRepository.findById(1L).get();
        assertThat(savedStudent).isNotNull();
        assertThat(student.getName()).isEqualTo(savedStudent.getName());
        assertThat(student.getEmail()).isEqualTo(savedStudent.getEmail());
        assertThat(student.getBudget()).isEqualTo(savedStudent.getBudget());
    }
}
