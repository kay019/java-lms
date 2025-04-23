package nextstep.courses.domain;

import nextstep.courses.infrastructure.JdbcSessionRepository;
import nextstep.students.domain.Student;
import nextstep.students.domain.StudentRepository;
import nextstep.students.domain.Students;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@JdbcTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=always",
        "spring.sql.init.schema-locations=classpath:schema.sql",
        "spring.sql.init.data-locations="
})
class SessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Mock
    private StudentRepository studentRepository;
    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, studentRepository);
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
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new CoverImage(20 * 1024, "PNG", 300, 200),
                SessionLifeCycle.READY,
                SessionRecruitStatus.OPEN,
                10,
                10000L
        );
        int count = sessionRepository.save(session);
        assertEquals(1, count);
    }

    @Test
    void 저장_무료강의() {
        Session session = new FreeSession(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new CoverImage(20 * 1024, "PNG", 300, 200),
                SessionLifeCycle.READY,
                SessionRecruitStatus.OPEN
        );
        int count = sessionRepository.save(session);
        assertEquals(1, count);
    }

    @Test
    void 조회_유료강의() {
        Session session = new PaidSession(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new CoverImage(20 * 1024, "PNG", 600, 400),
                SessionLifeCycle.READY,
                SessionRecruitStatus.OPEN,
                10,
                10000L
        );
        sessionRepository.save(session);
        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession).isNotNull();
        assertThat(session.getPeriod().getStartDate()).isEqualTo(savedSession.getPeriod().getStartDate());
        assertThat(session.getPeriod().getEndDate()).isEqualTo(savedSession.getPeriod().getEndDate());
        assertThat(session.getCoverImage().getSize()).isEqualTo(savedSession.getCoverImage().getSize());
        assertThat(session.getCoverImage().getType()).isEqualTo(savedSession.getCoverImage().getType());
        assertThat(session.getCoverImage().getWidth()).isEqualTo(savedSession.getCoverImage().getWidth());
        assertThat(session.getCoverImage().getHeight()).isEqualTo(savedSession.getCoverImage().getHeight());
        assertThat(session.getStatus().getLifeCycle()).isEqualTo(savedSession.getStatus().getLifeCycle());
        assertThat(session.getStatus().getRecruitStatus()).isEqualTo(savedSession.getStatus().getRecruitStatus());
        assertThat(session).isInstanceOf(PaidSession.class);
        assertThat(((PaidSession) session).getCapacity()).isEqualTo(10);
        assertThat(((PaidSession) session).getFee()).isEqualTo(10000L);
    }

    @Test
    void 조회_무료강의() {
        Session session = new FreeSession(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new CoverImage(20 * 1024, "PNG", 600, 400),
                SessionLifeCycle.READY,
                SessionRecruitStatus.OPEN
        );
        sessionRepository.save(session);
        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession).isNotNull();
        assertThat(session.getPeriod().getStartDate()).isEqualTo(savedSession.getPeriod().getStartDate());
        assertThat(session.getPeriod().getEndDate()).isEqualTo(savedSession.getPeriod().getEndDate());
        assertThat(session.getCoverImage().getSize()).isEqualTo(savedSession.getCoverImage().getSize());
        assertThat(session.getCoverImage().getType()).isEqualTo(savedSession.getCoverImage().getType());
        assertThat(session.getCoverImage().getWidth()).isEqualTo(savedSession.getCoverImage().getWidth());
        assertThat(session.getCoverImage().getHeight()).isEqualTo(savedSession.getCoverImage().getHeight());
        assertThat(session.getStatus().getLifeCycle()).isEqualTo(savedSession.getStatus().getLifeCycle());
        assertThat(session.getStatus().getRecruitStatus()).isEqualTo(savedSession.getStatus().getRecruitStatus());
        assertThat(session).isInstanceOf(FreeSession.class);
    }

    @Test
    void 조회_무료강의_학생있을때() {
        Student baek = new Student(1L, "baek", "baek@github.com", 10000L);
        Student shin = new Student(2L, "shin", "shin@github.com", 10000L);
        Session session = new FreeSession(
                1L,
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                new CoverImage(20 * 1024, "PNG", 600, 400),
                SessionLifeCycle.READY,
                SessionRecruitStatus.OPEN,
                new Students(List.of(baek, shin))
        );
        when(studentRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(baek, shin));
        sessionRepository.save(session);
        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession).isNotNull();
        assertThat(savedSession.getStudents().getSize()).isEqualTo(2);
        assertThat(savedSession.getStudents().getValues().get(0).getName()).isEqualTo("baek");
    }
}
