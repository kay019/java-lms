package nextstep.sessions.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import nextstep.payments.domain.Payment;
import nextstep.sessions.domain.cover.SessionCover;
import nextstep.sessions.domain.type.FreeSessionType;
import nextstep.sessions.domain.type.SessionType;

public class Session {
    private final Long id;

    private final LocalDateTime startAt;

    private final LocalDateTime endAt;

    private final SessionCover cover;

    private final SessionType sessionType;

    private final SessionStatus sessionStatus;

    private final Long currentMemberCount;

    public Session(SessionStatus sessionStatus) {
        this(null, null, null, null, new FreeSessionType(), sessionStatus, null);
    }

    public Session(Long id, LocalDateTime startAt, LocalDateTime endAt, SessionCover cover, SessionType sessionType,
                   SessionStatus sessionStatus, Long currentMemberCount) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cover = cover;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.currentMemberCount = currentMemberCount;
    }

    public boolean isRegisterable(Payment payment) {
        validateSessionInProgress();
        return sessionType.isRegisterable(payment, currentMemberCount);
    }

    private void validateSessionInProgress() {
        if (!sessionStatus.isSameAs(SessionStatus.ONGOING)) {
            throw new IllegalStateException("session is not in progress");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(startAt, session.startAt)
                && Objects.equals(endAt, session.endAt) && Objects.equals(cover, session.cover)
                && Objects.equals(sessionType, session.sessionType) && sessionStatus == session.sessionStatus
                && Objects.equals(currentMemberCount, session.currentMemberCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt, endAt, cover, sessionType, sessionStatus, currentMemberCount);
    }
}
