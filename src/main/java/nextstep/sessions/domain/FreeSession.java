package nextstep.sessions.domain;

import nextstep.users.domain.NsUser;

public class FreeSession extends Session {
    public FreeSession(Long id, Long courseId, Period sessionPeriod, ImageInfo imageInfo, SessionStatus sessionStatus, Registration registration) {
        super(id, courseId, sessionPeriod, imageInfo, sessionStatus, registration);
    }

    @Override
    protected boolean canRegister(NsUser user, Long amount) {
        return true;
    }
}
