package nextstep.courses.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sessions {
    private final List<Long> values;

    public Sessions(List<Long> values) {
        this.values = values;
    }

    public boolean isAlreadyIncluded(Long sessionId) {
        return values.stream().anyMatch(value -> Objects.equals(value, sessionId));
    }

    public Sessions addSession(Long sessionId) {
        return new Sessions(Stream.concat(values.stream(), Stream.of(sessionId)).collect(Collectors.toList()));
    }

    public List<Long> getSessionIds() {
        return values;
    }
}
