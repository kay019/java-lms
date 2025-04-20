package nextstep.session.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.enrollment.domain.Enrollment;
import nextstep.session.image.domain.ImageFileSize;
import nextstep.session.image.domain.ImageSize;
import nextstep.session.image.domain.ImageType;
import nextstep.session.image.domain.SessionCoverImage;
import nextstep.enrollment.domain.Student;
import nextstep.exception.FreeSessionIllegalArgumentException;
import nextstep.payments.domain.Payment;

import static nextstep.session.domain.SessionStatus.CLOSED;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FreeSessionTest {
    private Student student;
    private SessionCoverImage coverImage;
    private SessionStatus status;
    private SessionDate sessionDate;

    @BeforeEach
    void setUp() {
        student = new Student(1, JAVAJIGI, 1);
        coverImage = new SessionCoverImage(1, 1, new ImageFileSize(1024), ImageType.JPG, new ImageSize(300, 200));
        status = SessionStatus.ENROLLING;
        sessionDate = new SessionDate(LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 20));
    }

    @Test
    @DisplayName("FreeSession 빌더를 통해서 객체를 정상적으로 생성할 수 있다.")
    void FreeSession_생성_빌더_정상작동() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseID(1L)
            .coverImage(coverImage)
            .status(status)
            .sessionDate(sessionDate)
            .build();

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getCourseId()).isEqualTo(1L);
        assertThat(session.getCoverImage()).isEqualTo(coverImage);
        assertThat(session.getStatus()).isEqualTo(status);
        assertThat(session.getSessionDate()).isEqualTo(sessionDate);
        assertThat(session.getStudents()).isEmpty();
    }

    @Test
    @DisplayName("무료강의에 0원 결제를 통해 정상적으로 학생을 등록할 수 있다.")
    void enroll_정상_등록() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseID(1L)
            .coverImage(coverImage)
            .status(status)
            .sessionDate(sessionDate)
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
            .courseID(1L)
            .coverImage(coverImage)
            .status(status)
            .sessionDate(sessionDate)
            .build();

        Payment paidPayment = createPayment(1000);

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, paidPayment)))
            .isInstanceOf(FreeSessionIllegalArgumentException.class);
    }

    @Test
    @DisplayName("상태가 ENROLLING이 아니면 예외가 발생한다")
    void enroll_NullEnrollment_예외발생() {
        FreeSession session = new FreeSession.Builder()
            .id(1L)
            .courseID(1L)
            .coverImage(coverImage)
            .status(CLOSED)
            .sessionDate(sessionDate)
            .build();

        assertThatThrownBy(() -> session.enroll(new Enrollment(student, createPayment(0))))
            .isInstanceOf(FreeSessionIllegalArgumentException.class);
    }

    private Payment createPayment(long amount) {
        return new Payment("1", 1L, 1L, amount);
    }
}
