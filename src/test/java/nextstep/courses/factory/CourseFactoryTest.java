package nextstep.courses.factory;

import nextstep.courses.domain.session.Sessions;
import nextstep.courses.entity.CourseEntity;
import nextstep.stub.TestImageHandler;
import nextstep.stub.TestSessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CourseFactoryTest {

    @DisplayName("Course, Session DB 정보로 Course 인스턴스 생성")
    @Test
    public void testCreateCourse() {
        SessionFactory sessionFactory = new TestSessionFactory(
            new TestImageHandler(300, 200, 1024L * 866L),
            new Sessions()
        );

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
