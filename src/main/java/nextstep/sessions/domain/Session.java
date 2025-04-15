package nextstep.sessions.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import nextstep.sessions.domain.cover.SessionCover;

public class Session {
    private final LocalDateTime startAt;

    private final LocalDateTime endAt;

    private final SessionCover cover;

    public Session(LocalDateTime startAt, LocalDateTime endAt, SessionCover cover) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(startAt, session.startAt) && Objects.equals(endAt, session.endAt)
                && Objects.equals(cover, session.cover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, endAt, cover);
    }
}
