package nextstep.courses.domain;

public enum RecruitStatus {
    OPEN("모집중"), CLOSED("모집 마감");

    public final String description;

    RecruitStatus(String description) {
        this.description = description;
    }
}
