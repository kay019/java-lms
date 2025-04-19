package nextstep.courses.factory;

import nextstep.courses.domain.Course;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.SessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CourseFactory {

    private final SessionFactory sessionFactory;

    @Autowired
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
