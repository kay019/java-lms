package nextstep.courses.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sessions {
    private final List<Session> values;

    public Sessions(List<Session> values) {
        this.values = values;
    }

    public boolean contains(Session session) {
        Long id = session.getId();
        return values.stream().anyMatch(value -> value.getId().equals(id));
    }

    public Sessions addSession(Session session) {
        return new Sessions(Stream.concat(values.stream(), Stream.of(session)).collect(Collectors.toList()));
    }

    public List<Long> getSessionIds() {
        return values.stream()
                .map(Session::getId)
                .collect(Collectors.toList());
    }
}
