package nextstep.session.infrastructure;

import nextstep.courses.infrastructure.JdbcCoverImageRepository;
import nextstep.courses.infrastructure.JdbcSessionRepository;
import nextstep.payments.infrastructure.JdbcPaymentRepository;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.CoverImage;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionBuilder;
import nextstep.session.domain.SessionStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;
    private CoverImageRepository coverImageRepository;

    @BeforeEach
    void setUp() {
        this.coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);

        this.sessionRepository = new JdbcSessionRepository(
                jdbcTemplate,
                this.coverImageRepository,
                new JdbcPaymentRepository(jdbcTemplate)
        );
    }

    @Test
    void crud() {
        CoverImage coverImage = coverImageRepository.findById(1L);

        Session session = new SessionBuilder()
                .id(null)
                .paid(1000, 10)
                .sessionStatus(SessionStatus.RECRUITING)
                .coverImage(coverImage)
                .build();

        sessionRepository.save(session);

        Session newSession = sessionRepository.findById(session.getId());

        assertThat(newSession.getId()).isEqualTo(session.getId());
        assertThat(newSession.getCourseId()).isEqualTo(session.getCourseId());
        assertThat(newSession.getCoverImage().getId()).isEqualTo(session.getCoverImage().getId());
        assertThat(newSession.getSessionStatus()).isEqualTo(session.getSessionStatus());
        assertThat(newSession.getRegistrationPolicyType()).isEqualTo(session.getRegistrationPolicyType());
        assertThat(newSession.getSessionFee()).isEqualTo(session.getSessionFee());
        assertThat(newSession.getMaxStudentCount()).isEqualTo(session.getMaxStudentCount());
        assertThat(newSession.getStartedAt()).isEqualTo(session.getStartedAt());
        assertThat(newSession.getEndedAt()).isEqualTo(session.getEndedAt());
    }

}
