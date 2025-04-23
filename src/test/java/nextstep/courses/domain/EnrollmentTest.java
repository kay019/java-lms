package nextstep.courses.domain;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.EnrollmentStatus;
import nextstep.courses.enrollment.domain.Enrollments;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentTest {
    public static final Enrollment ENROLLMENT1 = Enrollment.enrollToGeneralCourse(1L, NsUserTest.JAVAJIGI, new Payment());
    public static final Enrollment ENROLLMENT2 = Enrollment.enrollToGeneralCourse(1L, NsUserTest.SANJIGI, new Payment());
    public static final Enrollments ENROLLMENTS = new Enrollments(new ArrayList<>(List.of(ENROLLMENT1, ENROLLMENT2)));


    @Test
    void 신청상태만_승인가능() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(ENROLLMENT1::approve);
    }
}
