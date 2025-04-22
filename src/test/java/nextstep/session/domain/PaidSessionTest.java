package nextstep.session.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.Student;
import nextstep.exception.PaidSessionIllegalArgumentException;
import nextstep.payments.domain.Payment;

import static nextstep.session.domain.SessionStatus.ENROLLING;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static nextstep.users.domain.NsUserTest.SANJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidSessionTest {
    private Student student;
    private SessionStatus status;
    private SessionDate sessionDate;

    @BeforeEach
    void setUp() {
        student = new Student(1, JAVAJIGI.getId(), 1, JAVAJIGI.getName());
        status = ENROLLING;
        sessionDate = new SessionDate(LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 20));
    }

    @Test
    @DisplayName("빌더로 PaidSession을 정상적으로 생성할 수 있다")
    void PaidSession_생성_빌더_정상작동() {
        PaidSession session = new PaidSession.Builder()
            .id(2L)
            .courseId(1L)
            .status(status)
            .sessionDate(sessionDate)
            .maxCapacity(50)
            .fee(30000)
            .build();

        assertThat(session.getId()).isEqualTo(2L);
        assertThat(session.getCourseId()).isEqualTo(1L);
        assertThat(session.getMaxCapacity()).isEqualTo(50);
        assertThat(session.getFee()).isEqualTo(30000);
        assertThat(session.getStudents()).isEmpty();
    }

    @Test
    @DisplayName("정상 결제로 학생을 등록할 수 있다")
    void enroll_정상_등록() {
        PaidSession session = new PaidSession.Builder()
            .maxCapacity(1)
            .status(ENROLLING)
            .fee(30000)
            .build();

        Payment correctPayment = createPayment(30000);

        session.enroll(new Enrollment(student, correctPayment));

        assertThat(session.getStudents())
            .hasSize(1)
            .containsExactly(student);
    }

    @Test
    @DisplayName("결제 금액이 다르면 예외가 발생한다")
    void enroll_잘못된_결제금액_예외발생() {
        PaidSession session = new PaidSession.Builder()
            .fee(30000)
            .build();

        Payment wrongAmountPayment = createPayment(25000);

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, wrongAmountPayment)))
            .isInstanceOf(PaidSessionIllegalArgumentException.class);
    }

    @Test
    @DisplayName("정원을 초과하여 등록하면 예외가 발생한다")
    void enroll_정원초과_예외발생() {
        PaidSession session = new PaidSession.Builder()
            .maxCapacity(1)
            .status(ENROLLING)
            .fee(30000)
            .build();

        Student secondStudent = new Student(1, SANJIGI.getId(), 1, SANJIGI.getName());
        Payment payment = createPayment(30000);

        session.enroll(new Enrollment(student, payment));

        assertThatThrownBy(() -> session.enroll(new Enrollment(secondStudent, payment)))
            .isInstanceOf(PaidSessionIllegalArgumentException.class);
    }

    @Test
    @DisplayName("상태가 ENROLLING이 아니면 예외가 발생한다")
    void enroll_상태가_ENROLLING이_아닐때_예외발생() {
        PaidSession session = new PaidSession.Builder()
            .status(SessionStatus.CLOSED)
            .fee(30000)
            .build();

        Payment payment = createPayment(30000);

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, payment)))
            .isInstanceOf(PaidSessionIllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미 등록한 학생이 Enrollment로 등록하면 예외가 발생한다")
    void enroll_학생이_중복으로_등록할때_예외발생() {
        PaidSession session = new PaidSession.Builder()
            .maxCapacity(1)
            .status(ENROLLING)
            .fee(30000)
            .build();

        Payment payment = createPayment(30000);
        session.enroll(new Enrollment(student, payment));
        assertThatThrownBy(() -> session.enroll(new Enrollment(student, payment)))
            .isInstanceOf(PaidSessionIllegalArgumentException.class);
    }

    private Payment createPayment(long amount) {
        return new Payment("1", 1L, 1L, amount);
    }
}
