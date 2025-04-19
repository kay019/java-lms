package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sessions {
    private final List<Session> value;

    public Sessions() {
        this(new ArrayList<>());
    }

    public Sessions(List<Session> value) {
        this.value = value;
    }

    public boolean add(Session session) {
        return value.add(session);
    }

    public void delete() {
        value.forEach(Session::delete);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessions sessions = (Sessions) o;
        return Objects.equals(value, sessions.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
