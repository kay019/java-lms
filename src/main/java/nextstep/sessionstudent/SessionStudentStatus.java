package nextstep.sessionstudent;

public enum SessionStudentStatus {
    PENDING,
    APPROVED,
    CANCELLED,
    ;

    public static SessionStudentStatus fromSelectRequired(boolean selectionRequired) {
        if (selectionRequired) {
            return PENDING;
        }

        return APPROVED;
    }
}
