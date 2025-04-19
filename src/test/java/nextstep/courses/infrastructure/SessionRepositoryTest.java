package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionDescriptor;
import nextstep.courses.domain.session.SessionPeriod;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.image.SessionImageType;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import nextstep.courses.entity.SessionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @DisplayName("강의 저장 테스트")
    @Test
    void testSave() throws IOException {
        Session session = createSampleSession();

        assertDoesNotThrow(() -> sessionRepository.save(session.toSessionEntity(1L)));
    }

    @DisplayName("강의 조회 테스트")
    @Test
    void testFindById() throws IOException {
        Session session = createSampleSession();

        long generatedId = sessionRepository.save(session.toSessionEntity(1L));
        assertThat(sessionRepository.findById(generatedId)).isNotNull();
    }

    @DisplayName("과정 ID로 모든 강의 찾기 테스트")
    @Test
    void testFindAllByCourseId() throws IOException {
        Long courseId = 1L;
        Session session1 = createSampleSession();
        Session session2 = createSampleSession();

        sessionRepository.save(session1.toSessionEntity(courseId));
        sessionRepository.save(session2.toSessionEntity(courseId));

        List<SessionEntity> sessions = sessionRepository.findAllByCourseId(courseId);

        assertThat(sessions).hasSize(2);
    }

    private Session createSampleSession() throws IOException {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage image(String url) {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public long byteSize(String url) {
                return 1024L * 866L;
            }
        };

        SessionConstraint constraint = new SessionConstraint(1000L, 50);

        SessionDescriptor descriptor = new SessionDescriptor(
            new SessionImage("http://test", imageHandlerStub, SessionImageType.JPEG),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new SessionEnrollPolicy()
        );

        return new Session(constraint, descriptor);
    }
}
