package nextstep.courses.domain;

public enum SessionRecruitmentState {
    ENDED,
    RECRUTING;

    public static boolean canNotRegister(SessionRecruitmentState sessionState) {
        return sessionState != RECRUTING;
    }
}
