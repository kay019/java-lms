package nextstep.sessionstudent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionStudentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionStudentRepository sessionStudentRepository;

    @BeforeEach
    void setUp() {
        sessionStudentRepository = new JdbcSessionStudentRepository(jdbcTemplate);
    }

    @Test
    void insertTest() {
        SessionStudent sessionStudent = new SessionStudent(1L, 1L, true);
        sessionStudentRepository.save(sessionStudent);

        SessionStudent newSessionStudent = sessionStudentRepository.findById(sessionStudent.getId());

        assertThat(newSessionStudent.getId()).isEqualTo(sessionStudent.getId());
        assertThat(newSessionStudent.getSessionId()).isEqualTo(sessionStudent.getSessionId());
        assertThat(newSessionStudent.getNsUserId()).isEqualTo(sessionStudent.getNsUserId());
        assertThat(newSessionStudent.getStatus()).isEqualTo(sessionStudent.getStatus());
        assertThat(newSessionStudent.getCreatedAt()).isEqualToIgnoringNanos(sessionStudent.getCreatedAt());
        assertThat(newSessionStudent.getUpdatedAt()).isEqualToIgnoringNanos(sessionStudent.getUpdatedAt());
    }

    @Test
    void updateTest() {
        SessionStudent sessionStudent = sessionStudentRepository.findById(1L);
        sessionStudent.cancelled(new ApprovalContext(true, true));
        sessionStudentRepository.save(sessionStudent);

        SessionStudent newSessionStudent = sessionStudentRepository.findById(1L);

        assertThat(newSessionStudent.getId()).isEqualTo(sessionStudent.getId());
        assertThat(newSessionStudent.getSessionId()).isEqualTo(sessionStudent.getSessionId());
        assertThat(newSessionStudent.getNsUserId()).isEqualTo(sessionStudent.getNsUserId());
        assertThat(newSessionStudent.getStatus()).isEqualTo(SessionStudentStatus.CANCELLED);
        assertThat(newSessionStudent.getCreatedAt()).isEqualToIgnoringNanos(sessionStudent.getCreatedAt());
        assertThat(newSessionStudent.getUpdatedAt()).isEqualToIgnoringNanos(sessionStudent.getUpdatedAt());
    }

}
