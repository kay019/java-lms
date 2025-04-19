package nextstep.stub;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.Sessions;
import nextstep.courses.domain.session.image.ImageHandler;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.factory.SessionFactory;

import java.util.List;

public class TestSessionFactory extends SessionFactory {
    private int createSessionCalled = 0;
    private int createSessionsCalled = 0;
    private Session createSessionResult;
    private Sessions createSessionsResult;

    public TestSessionFactory(ImageHandler imageHandler, Session createResult) {
        super(imageHandler);
        this.createSessionResult = createResult;
    }

    public TestSessionFactory(ImageHandler imageHandler, Sessions createResult) {
        super(imageHandler);
        this.createSessionsResult = createResult;
    }

    @Override
    public Session create(SessionEntity sessionEntity) {
        createSessionCalled++;
        return createSessionResult;
    }

    @Override
    public Sessions create(List<SessionEntity> sessionEntities) {
        createSessionsCalled++;
        return createSessionsResult;
    }

    public int getCreateSessionCalled() {
        return createSessionCalled;
    }

    public int getCreateSessionsCalled() {
        return createSessionsCalled;
    }
}
