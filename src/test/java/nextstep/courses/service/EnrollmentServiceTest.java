package nextstep.courses.service;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.EnrollmentStatus;
import nextstep.courses.enrollment.service.EnrollmentService;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnrollmentServiceTest {

    private EnrollmentService enrollmentService = new EnrollmentService();
    @Test
    void 강사만_승인_거절_가능() {
        Enrollment enrollment = Enrollment.enrollToSelectedCourse(1L, NsUserTest.JAVAJIGI, new Payment());
        enrollmentService.approve(enrollment, NsUserTest.INSTRUCTOR);
        Assertions.assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.APPROVED);
    }

    @Test
    void 강사_아니면_승인_거절_예외() {
        Enrollment enrollment = Enrollment.enrollToSelectedCourse(1L, NsUserTest.JAVAJIGI, new Payment());

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> enrollmentService.approve(enrollment, NsUserTest.JAVAJIGI));
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> enrollmentService.cancel(enrollment, NsUserTest.JAVAJIGI));
    }
}
