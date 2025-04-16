package nextstep.users.domain;

import java.util.Objects;

public class NsStudent {
    private NsUser user;
    private Long userId;
    private final Long registeredSessionId;

    public NsStudent(NsUser user, Long sessionId) {
        this.user = user;
        this.registeredSessionId = sessionId;
    }

    public NsStudent(Long userId, Long sessionId) {
        this.userId = userId;
        this.registeredSessionId = sessionId;
    }

    public boolean isSameUser(NsUser user) {
        return this.userId == user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NsStudent student = (NsStudent) o;
        return Objects.equals(userId, student.userId) && Objects.equals(registeredSessionId, student.registeredSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, registeredSessionId);
    }
}
