package nextstep.courses.infrastructure;

import nextstep.courses.record.CoverImageRecord;
import nextstep.courses.record.SessionRecord;
import nextstep.courses.domain.*;
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

    private JdbcCourseRepository courseRepository;
    private JdbcSessionRepository sessionRepository;
    private JdbcCoverImageRepository coverImageRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        courseRepository = new JdbcCourseRepository(jdbcTemplate);
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Course course = courseRepository.findById(1L);
        CoverImageRecord coverImageRecord = coverImageRepository.findById(1L);
        CoverImage coverImage = coverImageRecord.toCoverImage();

        Session session = new SessionBuilder()
                .id(null)
                .paid(1000, 10)
                .sessionStatus(SessionStatus.RECRUITING)
                .coverImage(coverImage)
                .course(course)
                .build();

        SessionRecord sessionRecord = SessionRecord.from(session);
        sessionRepository.save(sessionRecord);

        SessionRecord newSessionRecord = sessionRepository.findById(sessionRecord.getId());

        assertThat(newSessionRecord.getId()).isEqualTo(sessionRecord.getId());
        assertThat(newSessionRecord.getCourseId()).isEqualTo(sessionRecord.getCourseId());
        assertThat(newSessionRecord.getCoverImageId()).isEqualTo(sessionRecord.getCoverImageId());
        assertThat(newSessionRecord.getSessionStatus()).isEqualTo(sessionRecord.getSessionStatus());
        assertThat(newSessionRecord.getRegistrationPolicyType()).isEqualTo(sessionRecord.getRegistrationPolicyType());
        assertThat(newSessionRecord.getSessionFee()).isEqualTo(sessionRecord.getSessionFee());
        assertThat(newSessionRecord.getMaxStudentCount()).isEqualTo(sessionRecord.getMaxStudentCount());
        assertThat(newSessionRecord.getStartedAt()).isEqualTo(sessionRecord.getStartedAt());
        assertThat(newSessionRecord.getEndedAt()).isEqualTo(sessionRecord.getEndedAt());
    }

}
