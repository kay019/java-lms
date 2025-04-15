package nextstep.sessions.domain;

import nextstep.users.domain.NsUser;

public class PaidSession extends Session {
    private final Long fee;
    private final int maxStudentNumber;

    public PaidSession(Long id, Long courseId, Period sessionPeriod, ImageInfo imageInfo, SessionStatus sessionStatus, Registration registration, Long fee, int maxStudentNumber) {
        super(id, courseId, sessionPeriod, imageInfo, sessionStatus, registration);
        this.fee = fee;
        this.maxStudentNumber = maxStudentNumber;
    }

    @Override
    protected boolean canRegister(NsUser user, Long amount) {
        return fee.equals(amount) && maxStudentNumber > registeredUserCount();
    }
}
