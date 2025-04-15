package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private final List<Session> value;

    public Sessions() {
        this.value = new ArrayList<>();
    }

    public boolean add(Session session) {
        return value.add(session);
    }
}
