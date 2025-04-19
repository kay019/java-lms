package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.exception.EnrollmentIllegalArgumentException;
import nextstep.payments.domain.Payment;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnrollmentTest {
    @Test
    @DisplayName("학생과 결제 정보로 Enrollment를 생성할 수 있다")
    void createEnrollmentSuccess() {
        Student student = new Student(1, JAVAJIGI, 1);
        Payment payment = createPayment(1000);

        Enrollment enrollment = new Enrollment(student, payment);

        assertThat(enrollment.getStudent()).isEqualTo(student);
        assertThat(enrollment.isNotValid(1000)).isFalse();
        assertThat(enrollment.isNotValid(500)).isTrue();
    }

    @Test
    @DisplayName("학생이 null이면 EnrollmentIllegalArgumentException이 발생한다")
    void nullStudentThrowsException() {
        assertThatThrownBy(() -> new Enrollment(null, createPayment(500)))
            .isInstanceOf(EnrollmentIllegalArgumentException.class);
    }

    @Test
    @DisplayName("결제 정보가 null이면 EnrollmentIllegalArgumentException이 발생한다")
    void nullPaymentThrowsException() {
        Student student = new Student(1, JAVAJIGI, 1);

        assertThatThrownBy(() -> new Enrollment(student, null))
            .isInstanceOf(EnrollmentIllegalArgumentException.class);
    }

    private Payment createPayment(long amount) {
        return new Payment("1", 1L, 1L, amount);
    }
}
