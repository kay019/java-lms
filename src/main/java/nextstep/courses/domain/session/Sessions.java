package nextstep.courses.domain.session;

import java.util.List;

public class Sessions {
    private final List<Session> value;

    public Sessions(List<Session> value) {
        this.value = value;
    }

    public boolean add(Session session) {
        return value.add(session);
    }

    public void delete() {
        value.forEach(Session::delete);
    }
}
