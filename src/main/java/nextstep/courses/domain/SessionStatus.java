package nextstep.courses.domain;

public class SessionStatus {
    private final Phase phase;
    private final RecruitStatus recruitStatus;

    public SessionStatus(Phase phase, RecruitStatus recruitStatus) {
        this.phase = phase;
        this.recruitStatus = recruitStatus;
    }

    public Phase getPhase() {
        return phase;
    }

    public RecruitStatus getRecruitStatus() {
        return recruitStatus;
    }

    public boolean isInRecruit() {
        return recruitStatus == RecruitStatus.OPEN;
    }
}
