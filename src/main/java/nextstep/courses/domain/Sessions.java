package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private final List<Session> sessions;

    public Sessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Sessions() {
        this(new ArrayList<>());
    }

    public void add(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
