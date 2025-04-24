package nextstep.courses.domain;

public enum SessionRecruitmentState {
    ENDED,
    RECRUTING;

    public boolean canNotRegister() {
        return this != RECRUTING;
    }
}
