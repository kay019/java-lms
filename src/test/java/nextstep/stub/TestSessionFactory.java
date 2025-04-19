package nextstep.stub;

import nextstep.courses.domain.session.Sessions;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.factory.SessionFactory;

import java.util.List;

public class TestSessionFactory extends SessionFactory {
    int createCalled = 0;
    Sessions createResult;

    public TestSessionFactory(ImageHandler imageHandler, Sessions createResult) {
        super(imageHandler);
        this.createResult = createResult;
    }

    @Override
    public Sessions create(List<SessionEntity> sessionEntities) {
        createCalled++;
        return createResult;
    }

    public int getCreateCalled() {
        return createCalled;
    }
}
