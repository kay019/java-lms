package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.repository.SessionRepository;
import nextstep.courses.domain.repository.StudentRepository;
import nextstep.support.builder.FreeSessionBuilder;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcStudentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentRepository studentRepository;
    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
        sessionRepository = new JdbcFreeSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();
        Student student1 = new Student(NsUserTest.JAVAJIGI);
        Student student2 = new Student(NsUserTest.SANJIGI);
        List<Student> students = List.of(student1, student2);

        // when
        sessionRepository.save(freeSession);
        studentRepository.save(1L, 1L);
        studentRepository.save(2L, 1L);
        List<Student> result = studentRepository.findAllBySessionId(1L);

        // then
        assertThat(result.size()).isEqualTo(2);
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getUserId()).isEqualTo(students.get(i).getUserId());
            assertThat(result.get(i).getName()).isEqualTo(students.get(i).getName());
            assertThat(result.get(i).getEmail()).isEqualTo(students.get(i).getEmail());
        }
    }
}
