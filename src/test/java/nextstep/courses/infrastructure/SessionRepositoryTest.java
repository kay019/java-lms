package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

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
        JdbcCoverImageDao jdbcCoverImageDao = new JdbcCoverImageDao(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, jdbcCoverImageDao);
        try {
            jdbcTemplate.execute("DELETE FROM session");
            jdbcTemplate.execute("ALTER TABLE session ALTER COLUMN id RESTART WITH 1");
            System.out.println("테이블 초기화 성공");
        } catch (Exception e) {
            System.err.println("테이블 초기화 실패: " + e.getMessage());
        }
    }

    @Test
    void 저장_유료강의() {
        Session session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                new CoverImages(List.of(new CoverImage(20 * 1024, "PNG", 300, 200))),
                new SessionStatus(Phase.READY, RecruitStatus.OPEN),
                10,
                10000L
        );
        int count = sessionRepository.save(session);
        assertEquals(1, count);
    }

    @Test
    void 저장_무료강의() {
        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                new CoverImages(List.of(new CoverImage(20 * 1024, "PNG", 300, 200))),
                new SessionStatus(Phase.READY, RecruitStatus.OPEN)
        );
        int count = sessionRepository.save(session);
        assertEquals(1, count);
    }

    @Test
    void 조회_유료강의() {
        Session session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                new CoverImages(List.of(new CoverImage(20 * 1024, "PNG", 600, 400))),
                new SessionStatus(Phase.READY, RecruitStatus.OPEN),
                10,
                10000L
        );
        sessionRepository.save(session);
        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession).isNotNull();
        assertThat(session.getPeriod().getStartDate()).isEqualTo(savedSession.getPeriod().getStartDate());
        assertThat(session.getPeriod().getEndDate()).isEqualTo(savedSession.getPeriod().getEndDate());
        CoverImage sessionCoverImage = session.getCoverImages().getValues().get(0);
        CoverImage savedSessionCoverImage = session.getCoverImages().getValues().get(0);
        assertThat(sessionCoverImage.getSize()).isEqualTo(savedSessionCoverImage.getSize());
        assertThat(sessionCoverImage.getType()).isEqualTo(savedSessionCoverImage.getType());
        assertThat(sessionCoverImage.getWidth()).isEqualTo(savedSessionCoverImage.getWidth());
        assertThat(sessionCoverImage.getHeight()).isEqualTo(savedSessionCoverImage.getHeight());
        assertThat(session.getStatus().getPhase()).isEqualTo(savedSession.getStatus().getPhase());
        assertThat(session.getStatus().getRecruitStatus()).isEqualTo(savedSession.getStatus().getRecruitStatus());
        assertThat(session).isInstanceOf(PaidSession.class);
        assertThat(((PaidSession) session).getCapacity()).isEqualTo(10);
        assertThat(((PaidSession) session).getFee()).isEqualTo(10000L);
    }

    @Test
    void 조회_무료강의() {
        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                new CoverImages(List.of(new CoverImage(20 * 1024, "PNG", 600, 400))),
                new SessionStatus(Phase.READY, RecruitStatus.OPEN)
        );
        sessionRepository.save(session);
        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession).isNotNull();
        assertThat(session.getPeriod().getStartDate()).isEqualTo(savedSession.getPeriod().getStartDate());
        assertThat(session.getPeriod().getEndDate()).isEqualTo(savedSession.getPeriod().getEndDate());
        CoverImage sessionCoverImage = session.getCoverImages().getValues().get(0);
        CoverImage savedSessionCoverImage = session.getCoverImages().getValues().get(0);
        assertThat(sessionCoverImage.getSize()).isEqualTo(savedSessionCoverImage.getSize());
        assertThat(sessionCoverImage.getType()).isEqualTo(savedSessionCoverImage.getType());
        assertThat(sessionCoverImage.getWidth()).isEqualTo(savedSessionCoverImage.getWidth());
        assertThat(sessionCoverImage.getHeight()).isEqualTo(savedSessionCoverImage.getHeight());
        assertThat(session.getStatus().getPhase()).isEqualTo(savedSession.getStatus().getPhase());
        assertThat(session.getStatus().getRecruitStatus()).isEqualTo(savedSession.getStatus().getRecruitStatus());
        assertThat(session).isInstanceOf(FreeSession.class);
    }
}
