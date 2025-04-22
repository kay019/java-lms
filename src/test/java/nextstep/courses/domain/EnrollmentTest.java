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
    void 강사만_승인_거절_가능() {
        Enrollment enrollment = Enrollment.enrollToSelectedCourse(1L, NsUserTest.JAVAJIGI, new Payment());
        enrollment.approve(NsUserTest.INSTRUCTOR);
        Assertions.assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.APPROVED);
    }

    @Test
    void 강사_아니면_승인_거절_예외() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> ENROLLMENT1.approve(NsUserTest.SANJIGI));
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> ENROLLMENT1.cancel(NsUserTest.SANJIGI));

    }
}
