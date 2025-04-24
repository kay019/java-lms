package nextstep.courses.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnrollmentTest {
    @Test
    @DisplayName("수강 신청을 하면 status = REQUESTED 이다.")
    void requestEnrollTest() {
        Enrollment enrollment = Enrollment.requestEnroll(1L, 1L);

        Assertions.assertEquals(enrollment.getStatus(), RequestStatus.REQUESTED);
    }

    @Test
    @DisplayName("수강 승인을 하면 status = APPROVED 로 변경된다.")
    void approveTest() {
        Enrollment enrollment = Enrollment.requestEnroll(1L, 1L);

        enrollment.approve();

        Assertions.assertEquals(enrollment.getStatus(), RequestStatus.APPROVED);
    }

    @Test
    @DisplayName("수강 취소를 하면 status = REJECTED 로 변경된다.")
    void rejectedTest() {
        Enrollment enrollment = Enrollment.requestEnroll(1L, 1L);

        enrollment.reject();

        Assertions.assertEquals(enrollment.getStatus(), RequestStatus.REJECTED);
    }

}
