package nextstep.session.infrastructure;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import nextstep.session.domain.Student;
import nextstep.session.domain.PaidSession;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionDate;
import nextstep.session.domain.SessionStatus;
import nextstep.session.repository.SessionRepository;

import static nextstep.session.domain.EnrollmentStatus.ENROLLING;
import static nextstep.session.domain.SessionProgressStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SessionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcSessionRepository(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Test
    @DisplayName("PaidSession을 저장하고 동일한 ID로 조회하면 동일한 값을 반환해야 함")
    void saveAndFindPaidSession() {
        PaidSession paidSession = new PaidSession.Builder()
            .id(1L)
            .courseId(100L)
            .status(new SessionStatus(IN_PROGRESS, ENROLLING))
            .fee(50000)
            .maxCapacity(30)
            .sessionDate(new SessionDate(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)))
            .students(List.of(
                new Student(1L, 1000L, 1L, "홍길동"),
                new Student(2L, 1001L, 1L, "김철수")))
            .build();

        int saveResult = repository.save(paidSession);
        Session savedSession = repository.findById(1L);

        assertThat(saveResult).isEqualTo(3);
        assertThat(savedSession)
            .usingRecursiveComparison()
            .isEqualTo(paidSession);

        LOGGER.debug("Session: {}", savedSession);
    }
}
