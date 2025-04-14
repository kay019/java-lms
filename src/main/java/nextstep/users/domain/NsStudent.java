package nextstep.users.domain;

import java.util.Objects;

public class NsStudent {
    private final NsUser user;
    private final Long registeredSessionId;

    public NsStudent(NsUser user, Long sessionId) {
        this.user = user;
        this.registeredSessionId = sessionId;
    }

    public boolean isSameUser(NsUser user) {
        return this.user == user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NsStudent student = (NsStudent) o;
        return Objects.equals(user, student.user) && Objects.equals(registeredSessionId, student.registeredSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, registeredSessionId);
    }
}
