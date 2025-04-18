package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.*;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.domain.session.image.SessionImage;
import nextstep.courses.domain.session.image.SessionImageType;
import nextstep.courses.domain.session.policy.SessionEnrollPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void testSave() {
        Session session = createSampleSession();

        assertDoesNotThrow(() -> sessionRepository.save(session, 1L));
    }

    @DisplayName("강의 조회 테스트")
    @Test
    void testFindById() {
        Session session = createSampleSession();

        long generatedId = sessionRepository.save(session, 1L);
        assertThat(sessionRepository.findById(generatedId)).isNotNull();
    }

    @DisplayName("여러 강의 저장 테스트")
    @Test
    void testSaveAll() {
        int size = 2;
        Sessions sessions = createSampleSessions(size);

        int insertedCount = sessionRepository.saveAll(sessions, 1L);
        assertThat(insertedCount).isEqualTo(size);
    }

    private Session createSampleSession() {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage image() {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public void updateImage() { }

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

        return new Session(constraint, descriptor);
    }

    private Sessions createSampleSessions(int size) {
        List<Session> sessionList = Stream.generate(this::createSampleSession)
            .limit(size)
            .collect(Collectors.toList());
        return new Sessions(sessionList);
    }
}
