package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionStudentRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SessionStudentRepository sessionStudentRepository;

    @BeforeEach
    void setUp() {
        sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
        jdbcTemplate.update("INSERT INTO session_student (session_id, student_id) VALUES (1, 1)");
        jdbcTemplate.update("INSERT INTO session_student (session_id, student_id) VALUES (1, 2)");
        jdbcTemplate.update("INSERT INTO session_student (session_id, student_id) VALUES (2, 2)");
    }

    @Test
    void 세션_학생_조회_테스트() {
        List<Long> studentIdsBySession = sessionStudentRepository.findStudentIdsBySession(1L);
        assertThat(studentIdsBySession).containsExactlyInAnyOrder(1L, 2L);

        List<Long> studentIdsBySession2 = sessionStudentRepository.findStudentIdsBySession(2L);
        assertThat(studentIdsBySession2).containsExactlyInAnyOrder(2L);

        List<Long> sessionIdsByStudent = sessionStudentRepository.findSessionIdsByStudent(2L);
        assertThat(sessionIdsByStudent).containsExactlyInAnyOrder(1L, 2L);

        List<Long> sessionIdsByStudent2 = sessionStudentRepository.findSessionIdsByStudent(1L);
        assertThat(sessionIdsByStudent2).containsExactlyInAnyOrder(1L);
    }
}
