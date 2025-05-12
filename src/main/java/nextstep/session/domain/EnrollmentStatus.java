package nextstep.session.domain;

import java.util.Arrays;

public enum EnrollmentStatus {
    ENROLLED("모집 중"), NOT_ENROLLED("비 모집 중");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public static EnrollmentStatus from(String name) {
        return EnrollmentStatus.valueOf(name);
    }


    public boolean isSameAs(EnrollmentStatus enrollmentStatus) {
        return this == enrollmentStatus;
    }

    public String getDescription() {
        return this.description;
    }
}
