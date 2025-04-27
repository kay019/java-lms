package nextstep.courses.enrollment.service;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.users.domain.NsUser;

public class EnrollmentService {
    public void approve(Enrollment enrollment, NsUser user) {
        if (user.isNotInstructor()) {
            throw new IllegalArgumentException("강사만 승인할 수 있습니다.");
        }
        enrollment.approve();
    }

    public void cancel(Enrollment enrollment, NsUser user) {
        if (user.isNotInstructor()) {
            throw new IllegalArgumentException("강사만 취소할 수 있습니다.");
        }
        enrollment.cancel();
    }
}
