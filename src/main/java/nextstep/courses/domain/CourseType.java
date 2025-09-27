package nextstep.courses.domain;

public enum CourseType {
    PREMIUM, BASIC;

    public static CourseType getCourseType(String courseType) {
        return CourseType.valueOf(courseType.toUpperCase());
    }
}

