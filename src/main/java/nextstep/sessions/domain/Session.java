package nextstep.sessions.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import nextstep.sessions.domain.cover.SessionCover;

public class Session {
    private final LocalDateTime startAt;

    private final LocalDateTime endAt;

    private final SessionCover cover;

    private final SessionStatus sessionStatus;

    public Session(LocalDateTime startAt, LocalDateTime endAt, SessionCover cover, SessionStatus sessionStatus) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.cover = cover;
        this.sessionStatus = sessionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(startAt, session.startAt) && Objects.equals(endAt, session.endAt)
                && Objects.equals(cover, session.cover) && sessionStatus == session.sessionStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, endAt, cover, sessionStatus);
    }
}
