package nextstep.session.domain;

import java.util.List;

public class Sessions {
    private final List<Session> value;

    public Sessions(List<Session> sessions) {
        this.value = sessions;
    }

    public void add(Session session) {
        value.add(session);
    }
}
