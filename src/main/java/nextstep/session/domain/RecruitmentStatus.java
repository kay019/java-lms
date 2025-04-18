package nextstep.session.domain;

public enum RecruitmentStatus {
    RECRUITING,
    NOT_RECRUITING,
    ;

    public boolean isRecruiting() {
        return this == RECRUITING;
    }
}
