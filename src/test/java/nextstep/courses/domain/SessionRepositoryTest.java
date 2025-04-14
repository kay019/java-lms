package nextstep.courses.domain;

import nextstep.courses.infrastructure.JdbcSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=always",
        "spring.sql.init.schema-locations=classpath:schema.sql",
        "spring.sql.init.data-locations="
})
class SessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void 저장() {
        Session session = new Session(LocalDate.of(2025, 4, 15), LocalDate.of(2025, 5, 15), new CoverImage(20 * 1024, "PNG", 300, 200), SessionStatus.READY);
        int count = sessionRepository.save(session);
        assertEquals(1, count);
    }

    @Test
    void 조회() {
        Session session = new Session(LocalDate.of(2025, 4, 15), LocalDate.of(2025, 5, 15), new CoverImage(20 * 1024, "PNG", 600, 400), SessionStatus.READY);
        sessionRepository.save(session);
        Session savedSession;
        try {
            savedSession = sessionRepository.findById(1L);
        } catch (EmptyResultDataAccessException e) {
            savedSession = sessionRepository.findById(2L);
        }
        assertThat(session.getStartDate()).isEqualTo(savedSession.getStartDate());
        assertThat(session.getEndDate()).isEqualTo(savedSession.getEndDate());
        assertThat(session.getCoverImage().getSize()).isEqualTo(savedSession.getCoverImage().getSize());
        assertThat(session.getCoverImage().getType()).isEqualTo(savedSession.getCoverImage().getType());
        assertThat(session.getCoverImage().getWidth()).isEqualTo(savedSession.getCoverImage().getWidth());
        assertThat(session.getCoverImage().getHeight()).isEqualTo(savedSession.getCoverImage().getHeight());
        assertThat(session.getStatus()).isEqualTo(savedSession.getStatus());
        assertThat(session.getCapacity()).isNull();
        assertThat(session.getFee()).isNull();
    }
}