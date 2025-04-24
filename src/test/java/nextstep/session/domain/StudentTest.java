package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.session.exception.RegistrationStateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentTest {
    @Test
    @DisplayName("markAsSelected: PENDING 상태에서 SELECTED로 변경 성공")
    void markAsSelected_Success() {
        Student student = new Student(1L, 100L, 10L, "Alice");
        student.markAsSelected();

        assertThat(student.getStatus()).isEqualTo(RegistrationStatus.SELECTED);
    }

    @Test
    @DisplayName("markAsSelected: PENDING이 아닌 상태에서 변경 시도 시 예외 발생")
    void markAsSelected_Fail_WhenNotPending() {
        Student student = new Student(1L, 100L, 10L, "Alice");
        student.markAsSelected();

        assertThatThrownBy(student::markAsSelected)
            .isInstanceOf(RegistrationStateException.class);
    }

    @Test
    @DisplayName("markAsApproved: SELECTED 상태에서 APPROVED로 변경 성공")
    void markAsApproved_Success() {
        Student student = new Student(1L, 100L, 10L, "Alice");
        student.markAsSelected();
        student.markAsApproved();

        assertThat(student.getStatus()).isEqualTo(RegistrationStatus.APPROVED);
    }

    @Test
    @DisplayName("markAsApproved: SELECTED가 아닌 상태에서 변경 시도 시 예외 발생")
    void markAsApproved_Fail_WhenNotSelected() {
        Student student = new Student(1L, 100L, 10L, "Alice");

        assertThatThrownBy(student::markAsApproved)
            .isInstanceOf(RegistrationStateException.class);
    }

    @Test
    @DisplayName("markAsCanceled: SELECTED 상태에서 변경 시도 시 예외 발생")
    void markAsCanceled_Fail_WhenSelected() {
        Student student = new Student(1L, 100L, 10L, "Alice");
        student.markAsSelected();

        assertThatThrownBy(student::markAsCanceled)
            .isInstanceOf(RegistrationStateException.class);
    }

    @Test
    @DisplayName("markAsCanceled: PENDING 상태에서 CANCELED로 변경 성공")
    void markAsCanceled_Success_WhenPending() {
        Student student = new Student(1L, 100L, 10L, "Alice");
        student.markAsCanceled();

        assertThat(student.getStatus()).isEqualTo(RegistrationStatus.CANCELED);
    }

    @Test
    @DisplayName("markAsCanceled: APPROVED 상태에서 CANCELED로 변경 실패")
    void markAsCanceled_Success_WhenApproved() {
        Student student = new Student(1L, 100L, 10L, "Alice");
        student.markAsSelected();
        student.markAsApproved();

        assertThatThrownBy(student::markAsCanceled)
            .isInstanceOf(RegistrationStateException.class);
    }
}
