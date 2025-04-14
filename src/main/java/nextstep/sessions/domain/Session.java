package nextstep.sessions.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {
    private LocalDateTime startAt;

    private LocalDateTime endAt;

    public Session(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(startAt, session.startAt) && Objects.equals(endAt, session.endAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, endAt);
    }
}
