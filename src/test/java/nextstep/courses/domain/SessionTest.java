package nextstep.courses.domain;

import nextstep.students.domain.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionTest {
    @Test
    void 수강신청_성공() {
        Session session = new FreeSession(LocalDate.now(), LocalDate.now().plusDays(1), null, SessionLifeCycle.READY, SessionRecruitStatus.OPEN);
        Student student = new Student("baek", "sh@github.com", 10000L);
        assertDoesNotThrow(() -> session.registerStudent(student));
    }

    @Test
    void 정합성테스트_실패_세션상태() {
        Session session = new FreeSession(LocalDate.now(), LocalDate.now().plusDays(1), null, SessionLifeCycle.READY, SessionRecruitStatus.CLOSED);
        Student student = new Student("baek", "sh@github.com", 10000L);
        assertThatThrownBy(() -> session.registerStudent(student))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
    }

    @Test
    void 정합성테스트_실패_정원초과() {
        Session session = new PaidSession(0L, LocalDate.now(), LocalDate.now().plusDays(1), null, SessionLifeCycle.READY, SessionRecruitStatus.OPEN, 1, 10000L);
        Student student1 = new Student("baek", "sh@github.com", 10000L);
        Student student2 = new Student("shin", "hj@github.com", 10000L);
        session.registerStudent(student1);
        assertThatThrownBy(() -> session.registerStudent(student2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정원이 초과되었습니다.");
    }

    @Test
    void 정합성테스트_실패_예산부족() {
        Session session = new PaidSession(0L, LocalDate.now(), LocalDate.now().plusDays(1), null, SessionLifeCycle.IN_PROGRESS, SessionRecruitStatus.OPEN, 1, 10000L);
        Student student = new Student("baek", "sh@github.com", 1000L);
        assertThatThrownBy(() -> session.registerStudent(student))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예산이 부족하여 강의를 신청할 수 없습니다.");
    }

    @Test
    void 정합성테스트_실패_이미등록한강의() {
        Session session = new PaidSession(0L, LocalDate.now(), LocalDate.now().plusDays(1), null, SessionLifeCycle.IN_PROGRESS, SessionRecruitStatus.OPEN, 3, 10000L);
        Student student1 = new Student("baek", "sh@github.com", 10000L);
        session.registerStudent(student1);
        assertThatThrownBy(() -> session.registerStudent(student1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 등록한 강의입니다.");
    }
}
