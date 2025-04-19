package nextstep.courses.factory;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.session.Sessions;
import nextstep.courses.domain.session.image.URLImageHandler;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.SessionEntity;

import java.io.IOException;
import java.util.List;

public class CourseFactory {

    private final SessionFactory sessionFactory;

    public CourseFactory() {
        this.sessionFactory = new SessionFactory();
    }

    public CourseFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Course create(CourseEntity courseEntity, List<SessionEntity> sessionEntities) throws IOException {
        return new Course(
            courseEntity.getId(),
            courseEntity.isDeleted(),
            courseEntity.getTitle(),
            courseEntity.getCreatorId(),
            sessionFactory.create(sessionEntities),
            courseEntity.getCreatedAt(),
            courseEntity.getUpdatedAt()
        );
    }
}
