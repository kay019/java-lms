package nextstep.session.domain;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private final List<Session> value;

    public Sessions() {
        this.value = new ArrayList<>();
    }

    public void add(Session session) {
        value.add(session);
    }
}
