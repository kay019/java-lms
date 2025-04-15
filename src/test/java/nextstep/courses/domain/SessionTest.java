package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionTest {
    @Test
    void session_addStudent_테스트() {
        Session session = new Session(LocalDate.now(), LocalDate.now().plusDays(1), null, SessionStatus.READY);
        assertThat(session.getStudentsCount()).isEqualTo(0);
        session.addStudent();
        assertThat(session.getStudentsCount()).isEqualTo(1);
    }

    @Test
    void 정합성테스트_성공() {
        CoverImage coverImage = new CoverImage(20 * 1024, "PNG", 300, 200);
        Session session = new Session(LocalDate.now(), LocalDate.now().plusDays(1), coverImage, SessionStatus.OPEN);
        assertDoesNotThrow(() -> session.validateEnrollment(10000L));
    }

    @Test
    void 정합성테스트_실패_세션상태() {
        CoverImage coverImage = new CoverImage(20 * 1024, "PNG", 300, 200);
        Session session = new Session(LocalDate.now(), LocalDate.now().plusDays(1), coverImage, SessionStatus.CLOSED);
        assertThatThrownBy(() -> session.validateEnrollment(10000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
    }

    @Test
    void 정합성테스트_실패_정원초과() {
        CoverImage coverImage = new CoverImage(20 * 1024, "PNG", 300, 200);
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusDays(1), coverImage, SessionStatus.OPEN, SessionType.PAID, 1, 50000);
        session.addStudent();
        assertThatThrownBy(() -> session.validateEnrollment(10000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정원이 초과되었습니다.");
    }

    @Test
    void 정합성테스트_실패_예산부족() {
        CoverImage coverImage = new CoverImage(20 * 1024, "PNG", 300, 200);
        Session session = new Session(1L, LocalDate.now(), LocalDate.now().plusDays(1), coverImage, SessionStatus.OPEN, SessionType.PAID, 1, 50000);
        assertThatThrownBy(() -> session.validateEnrollment(10000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예산이 부족하여 강의를 신청할 수 없습니다.");
    }
}