package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING,
    ENROLLING,
    CLOSED;

    public static SessionStatus from(String status) {
        return SessionStatus.valueOf(status);
    }

    public SessionStatus openEnrollment() {
        if (this != PREPARING) {
            throw new IllegalStateException("Session is not preparing");
        }

        return ENROLLING;
    }

    public SessionStatus closeEnrollment() {
        if (this != ENROLLING) {
            throw new IllegalStateException("Session is not enrolling");
        }

        return CLOSED;
    }

    public void assertCanEnroll() {
        if (this != ENROLLING) {
            throw new IllegalStateException("모집 중인 세션만 수강신청할 수 있습니다.");
        }
    }
    
}
