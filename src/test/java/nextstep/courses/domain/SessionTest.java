package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionTest {
    @Test
    void 수강신청_성공() {
        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                null,
                new SessionStatus(Phase.READY, RecruitStatus.OPEN)
        );
        Student student = new Student("baek", "sh@github.com", 10000L, new PremiumPlan(true, true));
        assertDoesNotThrow(() -> session.registerStudent(student));
    }

    @Test
    void 정합성테스트_실패_세션상태() {
        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                null,
                new SessionStatus(Phase.READY, RecruitStatus.CLOSED)
        );
        Student student = new Student("baek", "sh@github.com", 10000L, new PremiumPlan(true, true));
        assertThatThrownBy(() -> session.registerStudent(student))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("세션이 '모집 중' 일때만 수강 신청이 가능합니다.");
    }

    @Test
    void 정합성테스트_실패_정원초과() {
        Session session = new PaidSession(
                0L,
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                null,
                new SessionStatus(Phase.READY, RecruitStatus.OPEN),
                1,
                10000L
        );
        Student student1 = new Student("baek", "sh@github.com", 10000L, new PremiumPlan(true, true));
        Student student2 = new Student("shin", "hj@github.com", 10000L, new PremiumPlan(true, true));
        session.registerStudent(student1);
        assertThatThrownBy(() -> session.registerStudent(student2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정원이 초과되었습니다.");
    }

    @Test
    void 정합성테스트_실패_예산부족() {
        Session session = new PaidSession(
                0L,
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                null,
                new SessionStatus(Phase.IN_PROGRESS, RecruitStatus.OPEN),
                1,
                10000L
        );
        Student student = new Student("baek", "sh@github.com", 1000L, new PremiumPlan(true, true));
        assertThatThrownBy(() -> session.registerStudent(student))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예산이 부족하여 강의를 신청할 수 없습니다.");
    }

    @Test
    void 정합성테스트_실패_이미등록한강의() {
        Session session = new PaidSession(
                0L,
                new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                null,
                new SessionStatus(Phase.IN_PROGRESS, RecruitStatus.OPEN),
                3,
                10000L
        );
        Student student1 = new Student("baek", "sh@github.com", 10000L, new PremiumPlan(true, true));
        session.registerStudent(student1);
        assertThatThrownBy(() -> session.registerStudent(student1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 등록한 강의입니다.");
    }
}
