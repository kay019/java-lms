package nextstep.courses.factory;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.Sessions;
import nextstep.courses.entity.SessionEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SessionsFactory {

    private final SessionFactory sessionFactory;

    public SessionsFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Sessions create(List<SessionEntity> sessionEntities) throws IOException {
        List<Session> resultList = new ArrayList<>();
        for (SessionEntity sessionEntity : sessionEntities) {
            resultList.add(sessionFactory.create(sessionEntity));
        }
        return new Sessions(resultList);
    }
}
