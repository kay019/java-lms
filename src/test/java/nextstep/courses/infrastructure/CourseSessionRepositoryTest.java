package nextstep.courses.infrastructure;

import nextstep.courses.domain.CourseSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CourseSessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CourseSessionRepository courseSessionRepository;

    @BeforeEach
    void setUp() {
        courseSessionRepository = new JdbcCourseSessionRepository(jdbcTemplate);
        jdbcTemplate.update("INSERT INTO course_session (course_id, session_id) VALUES (1, 2)");
        jdbcTemplate.update("INSERT INTO course_session (course_id, session_id) VALUES (1, 3)");
    }

    @Test
    void 세션_조회_테스트() {
        List<Long> sessionIdsByCourse = courseSessionRepository.findSessionIdsByCourse(1L);
        assertThat(sessionIdsByCourse).containsExactlyInAnyOrder(2L, 3L);

        List<Long> courseIdsBySession = courseSessionRepository.findCourseIdsBySession(2L);
        assertThat(courseIdsBySession).containsExactlyInAnyOrder(1L);

        List<Long> courseIdsBySession2 = courseSessionRepository.findCourseIdsBySession(3L);
        assertThat(courseIdsBySession2).containsExactlyInAnyOrder(1L);
    }
}
