package nextstep.courses.domain;

public class SessionStatus {
    private final SessionLifeCycle lifeCycle;
    private final SessionRecruitStatus recruitStatus;

    public SessionStatus(SessionLifeCycle lifeCycle, SessionRecruitStatus recruitStatus) {
        this.lifeCycle = lifeCycle;
        this.recruitStatus = recruitStatus;
    }

    public SessionLifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public SessionRecruitStatus getRecruitStatus() {
        return recruitStatus;
    }

    public boolean isInRecruit() {
        return recruitStatus == SessionRecruitStatus.OPEN;
    }
}
