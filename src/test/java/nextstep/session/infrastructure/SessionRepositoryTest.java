package nextstep.session.infrastructure;

import nextstep.courses.infrastructure.JdbcCoverImageRepository;
import nextstep.courses.infrastructure.JdbcSessionRepository;
import nextstep.payments.infrastructure.JdbcPaymentRepository;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.CoverImageTest;
import nextstep.session.domain.RecruitmentStatus;
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

    @BeforeEach
    void setUp() {
        this.sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Session session = new SessionBuilder()
                .id(null)
                .paid(1000, 10)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .build();

        sessionRepository.save(session);

        Session newSession = sessionRepository.findById(session.getId());

        assertThat(newSession.getId()).isEqualTo(session.getId());
        assertThat(newSession.getCourseId()).isEqualTo(session.getCourseId());
        assertThat(newSession.getProgressStatus()).isEqualTo(session.getProgressStatus());
        assertThat(newSession.getRecruitmentStatus()).isEqualTo(session.getRecruitmentStatus());
        assertThat(newSession.getRegistrationPolicyType()).isEqualTo(session.getRegistrationPolicyType());
        assertThat(newSession.getSessionFee()).isEqualTo(session.getSessionFee());
        assertThat(newSession.getMaxStudentCount()).isEqualTo(session.getMaxStudentCount());
        assertThat(newSession.getStartedAt()).isEqualTo(session.getStartedAt());
        assertThat(newSession.getEndedAt()).isEqualTo(session.getEndedAt());
    }

}
