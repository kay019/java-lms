package nextstep.courses.domain.session.inner;

public enum EnrollmentStatus {
    NOT_RECRUITING("비모집중"),    // 수강생 모집하지 않음
    RECRUITING("모집중");          // 수강생 모집 중

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean canEnroll() {
        return this == RECRUITING;
    }
}
