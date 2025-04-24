package nextstep.courses.domain;

public enum RequestStatus {
    REQUESTED("신청"),
    APPROVED("수락"),
    REJECTED("거절");

    private final String description;

    RequestStatus(String description) {
        this.description = description;
    }

    public void validateApprove() {
        if (this != REQUESTED) {
            throw new IllegalArgumentException("수강 신청 상태일 때만 수강 승인할 수 있습니다.");
        }
    }

    public void validateReject() {
        if (this != REQUESTED) {
            throw new IllegalArgumentException("수강 신청 상태일 때만 수강 취소할 수 있습니다.");
        }
    }

}
