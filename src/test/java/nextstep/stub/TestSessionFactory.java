package nextstep.stub;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.factory.SessionFactory;

public class TestSessionFactory extends SessionFactory {
    private int createCalled = 0;
    private Session createResult = null;

    public TestSessionFactory() {
        super(new TestImageHandler(300, 200, 1024L * 866L));
    }

    public TestSessionFactory(ImageHandler imageHandler) {
        super(imageHandler);
    }

    public TestSessionFactory(ImageHandler imageHandler, Session createResult) {
        super(imageHandler);
        this.createResult = createResult;
    }

    @Override
    public Session create(SessionEntity sessionEntity) {
        createCalled++;
        return createResult;
    }

    public int getCreateSessionCalled() {
        return createCalled;
    }
}
