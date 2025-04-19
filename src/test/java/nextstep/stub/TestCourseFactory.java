package nextstep.stub;

import nextstep.courses.domain.Course;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.factory.CourseFactory;
import nextstep.courses.factory.SessionFactory;

import java.util.List;

public class TestCourseFactory extends CourseFactory  {
    int createCalled = 0;
    Course createResult;

    public TestCourseFactory(SessionFactory sessionFactory, Course createResult) {
        super(sessionFactory);
        this.createResult = createResult;
    }

    @Override
    public Course create(CourseEntity courseEntity, List<SessionEntity> sessionEntities) {
        createCalled++;
        return createResult;
    }

    public int getCreateCalled() {
        return createCalled;
    }
}
