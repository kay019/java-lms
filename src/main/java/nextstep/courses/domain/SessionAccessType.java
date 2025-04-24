package nextstep.courses.domain;

public enum SessionAccessType {
    OPEN("APPROVED"),
    RESTRICTED("PENDING");

    private String applicationState;

    SessionAccessType(String applicationState) {
        this.applicationState = applicationState;
    }

    public boolean isOpen() {
        return this == OPEN;
    }

    public String getDefaultApplicationState() {
        return applicationState;
    }
}
