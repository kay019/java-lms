package nextstep.sessions.service;

import java.time.LocalDateTime;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.cover.SessionCover;
import nextstep.sessions.domain.type.SessionType;
import org.springframework.stereotype.Service;

@Service("sessionService")
public class SessionService {
    public void createSession(Long id, LocalDateTime startAt, LocalDateTime endAt, SessionCover cover,
                              SessionType sessionType, SessionStatus sessionStatus) {
        Session session = new Session(id, startAt, endAt, cover, sessionType, sessionStatus, 0L);
        // insert db
    }

    public void joinSession() {
        // TODO 목록, DB가 없으니까..
        // 1. find session
        // 2. find payment info
        // 3. session.register(payment);
        // 4. session.increaseMemberCount();
    }

    // ETC CRUD..
}
