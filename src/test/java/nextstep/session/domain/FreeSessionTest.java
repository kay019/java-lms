package nextstep.session.domain;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.Student;
import nextstep.payments.domain.Payment;
import nextstep.session.exception.FreeSessionDuplicateStudentException;
import nextstep.session.exception.FreeSessionInvalidEnrollmentException;
import nextstep.session.exception.FreeSessionNotEnrollingException;

import static nextstep.session.domain.EnrollmentStatus.ENROLLING;
import static nextstep.session.domain.SessionProgressStatus.CLOSED;
import static nextstep.session.domain.SessionProgressStatus.IN_PROGRESS;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FreeSessionTest {
    private Student student;
    private SessionStatus status;
    private SessionDate sessionDate;

    @BeforeEach
    void setUp() {
        student = new Student(1, JAVAJIGI.getId(), 1, JAVAJIGI.getName());
        status = new SessionStatus(IN_PROGRESS, ENROLLING);
        sessionDate = new SessionDate(LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 20));
    }

    @Test
    @DisplayName("FreeSession 빌더를 통해서 객체를 정상적으로 생성할 수 있다.")
    void FreeSession_생성_빌더_정상작동() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(status)
            .sessionDate(sessionDate)
            .students(new ArrayList<>())
            .build();

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getCourseId()).isEqualTo(1L);
        assertThat(session.getStatus()).isEqualTo(status);
        assertThat(session.getSessionDate()).isEqualTo(sessionDate);
        assertThat(session.getStudents()).isEmpty();
    }

    @Test
    @DisplayName("무료강의에 0원 결제를 통해 정상적으로 학생을 등록할 수 있다.")
    void enroll_정상_등록() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(status)
            .sessionDate(sessionDate)
            .students(new ArrayList<>())
            .build();

        Payment freePayment = createPayment(0);

        session.enroll(new Enrollment(student, freePayment));

        assertThat(session.getStudents())
            .hasSize(1)
            .contains(student);
    }

    @Test
    @DisplayName("무료 강의의 결제 금액이 0원이 아닌 경우 예외가 발생한다.")
    void enroll_유료_결제시_예외발생() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(status)
            .sessionDate(sessionDate)
            .students(new ArrayList<>())
            .build();

        Payment paidPayment = createPayment(1000);

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, paidPayment)))
            .isInstanceOf(FreeSessionInvalidEnrollmentException.class);
    }

    @Test
    @DisplayName("상태가 ENROLLING이 아니면 예외가 발생한다")
    void enroll_NullEnrollment_예외발생() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(new SessionStatus(CLOSED, ENROLLING))
            .sessionDate(sessionDate)
            .build();

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, createPayment(0))))
            .isInstanceOf(FreeSessionNotEnrollingException.class);
    }

    @Test
    @DisplayName("이미 등록한 학생이 Enrollment로 등록하면 예외가 발생한다")
    void enroll_학생이_중복으로_등록할때_예외발생() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseId(1L)
            .status(status)
            .sessionDate(sessionDate)
            .students(new ArrayList<>())
            .build();

        Payment freePayment = createPayment(0);
        session.enroll(new Enrollment(student, freePayment));

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, freePayment)))
            .isInstanceOf(FreeSessionDuplicateStudentException.class);
    }

    private Payment createPayment(long amount) {
        return new Payment("1", 1L, 1L, amount);
    }
}
