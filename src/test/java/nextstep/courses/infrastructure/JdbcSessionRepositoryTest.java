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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
class JdbcSessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @DisplayName("강의 저장 테스트")
    @Test
    @Order(1)
    void testSave() {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage image() {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public void updateImage() {
            }

            @Override
            public long byteSize() {
                return 1024L * 866L;
            }
        };

        SessionConstraint constraint = new SessionConstraint(1000L, 50);

        SessionDescriptor descriptor = new SessionDescriptor(
            new SessionImage("http://test", imageHandlerStub, SessionImageType.JPEG),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new SessionEnrollPolicy()
        );

        Session session = new Session(constraint, descriptor);
        assertThat(sessionRepository.save(session, 1L)).isEqualTo(1);
    }

    @DisplayName("강의 조회 테스트")
    @Test
    @Order(2)
    void testFindById() throws IOException {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage image() {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public void updateImage() {
            }

            @Override
            public long byteSize() {
                return 1024L * 866L;
            }
        };

        SessionConstraint constraint = new SessionConstraint(1000L, 50);

        SessionDescriptor descriptor = new SessionDescriptor(
            new SessionImage("http://test", imageHandlerStub, SessionImageType.JPEG),
            new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            new SessionEnrollPolicy()
        );

        Session session = new Session(constraint, descriptor);
        sessionRepository.save(session, 1L);
        assertThat(sessionRepository.findById(2L)).isNotNull();
    }
}
