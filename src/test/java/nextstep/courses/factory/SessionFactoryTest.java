package nextstep.courses.factory;

import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.entity.SessionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionFactoryTest {

    @DisplayName("Session DB 정보로 Session 인스턴스 생성")
    @Test
    public void testCreateSession() {
        SessionEntity sessionEntity = createSessionEntity(1L);

        SessionFactory sessionFactory = new SessionFactory(new ImageHandler() {
            @Override
            public BufferedImage image(String url) {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public long byteSize(String url) {
                return 1024L * 866L;
            }
        });
        assertDoesNotThrow(() -> sessionFactory.create(sessionEntity));
    }

    @DisplayName("Session DB 정보들로 Sessions 인스턴스 생성")
    @Test
    public void testCreateSessions() {
        List<SessionEntity> sessionEntities = List.of(createSessionEntity(1L),  createSessionEntity(2L));
        SessionFactory sessionFactory = new SessionFactory(new ImageHandler() {
            @Override
            public BufferedImage image(String url) {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public long byteSize(String url) {
                return 1024L * 866L;
            }
        });

        assertDoesNotThrow(() -> sessionFactory.create(sessionEntities));
    }

    private SessionEntity createSessionEntity(Long id) {
        return SessionEntity.builder()
            .id(id)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .deleted(false)
            .courseId(1L)
            .fee(200_000L)
            .capacity(80)
            .imageUrl("http://test")
            .imageType("JPG")
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now())
            .type("PAID")
            .status("ENROLLING")
            .build();
    }
}
