package nextstep.sessions.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Student {
    private final Long id;
    private final Long nsUserId;
    private final Long sessionId;
    private final LocalDateTime created_dt;

    public Student(Long nsUserId, Long sessionId) {
        this(null, nsUserId, sessionId, null);
    }

    public Student(Long id, Long nsUserId, Long sessionId, LocalDateTime created_dt) {
        this.id = id;
        this.nsUserId = nsUserId;
        this.sessionId = sessionId;
        this.created_dt = created_dt;
    }

    public boolean isSameUser(Long nsUserId) {
        return nsUserId.equals(this.nsUserId);
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(nsUserId, student.nsUserId) && Objects.equals(sessionId,
                student.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nsUserId, sessionId);
    }
}
