package nextstep.courses.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sessions {
    private final List<Session> values;

    public Sessions(List<Session> values) {
        this.values = values;
    }

    public boolean isAlreadyIncluded(Long sessionId) {
        return values.stream().anyMatch(session -> session.sessionIdMatches(sessionId));
    }

    public Sessions addSession(Session session) {
        return new Sessions(Stream.concat(values.stream(), Stream.of(session)).collect(Collectors.toList()));
    }
}
