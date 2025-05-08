package nextstep.session.domain;

import java.util.Arrays;

public enum EnrollmentStatus {
    ENROLLED("모집 중"), NOT_ENROLLED("비 모집 중");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public static EnrollmentStatus from(String description) {
        return Arrays.stream(values())
                .filter(status -> status.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for description: " + description));
    }


    public boolean isSameAs(EnrollmentStatus enrollmentStatus) {
        return this == enrollmentStatus;
    }

    public String getDescription() {
        return this.description;
    }
}
