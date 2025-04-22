package nextstep.courses.domain.session.inner;

public enum UserEnrollmentStatus {
    REQUESTED("신청"),    // 수강 신청
    ENROLLED("승인"),   // 수강 중
    NOT_AVAILABLE("불가"), // 수강 불가
    REJECTED("거절");    // 수강 거절

    private final String description;

    UserEnrollmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
