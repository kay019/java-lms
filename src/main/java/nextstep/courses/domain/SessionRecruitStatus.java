package nextstep.courses.domain;

public enum SessionRecruitStatus {
    OPEN("모집중"), CLOSED("모집 마감");

    public final String description;

    SessionRecruitStatus(String description) {
        this.description = description;
    }
}
