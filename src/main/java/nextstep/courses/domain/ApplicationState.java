package nextstep.courses.domain;

public enum ApplicationState {
    PENDING,
    APPROVED,
    REJECTED;

    public static boolean isPending(ApplicationState applicationState) {
        return applicationState == PENDING;
    }

    public static boolean isApproved(ApplicationState applicationState) {
        return applicationState == APPROVED;
    }

    public static boolean isRejected(ApplicationState applicationState) {
        return applicationState == REJECTED;
    }
}
