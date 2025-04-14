package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

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
}
