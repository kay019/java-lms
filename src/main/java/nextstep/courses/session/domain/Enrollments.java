package nextstep.courses.session.domain;

import java.util.List;

public class Enrollments {
    private final List<Enrollment> enrollments;

    public Enrollments (List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void add(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public void checkPossibleEnroll(int maxParticipants) {
        if(enrollments.size() >= maxParticipants) {
            throw new IllegalArgumentException("수강 인원이 초과되었습니다.");
        }
    }
}
