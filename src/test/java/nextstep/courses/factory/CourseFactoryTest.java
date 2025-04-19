package nextstep.courses.factory;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.SessionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CourseFactoryTest {

    @DisplayName("Course, Session DB 정보로 Course 인스턴스 생성")
    @Test
    public void testCreateCourse() {
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

        CourseFactory courseFactory = new CourseFactory(sessionFactory);

        CourseEntity courseEntity = CourseEntity.builder()
            .id(1L)
            .title("test-title")
            .creatorId(3L)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .deleted(false)
            .build();

        assertDoesNotThrow(() -> courseFactory.create(courseEntity, List.of()));
    }
}
