package nextstep.session.domain.property;

import nextstep.session.domain.constraint.SessionConstraint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionTypeTest {

    @DisplayName("무료강의는 수당신청을 조건없이 할 수 있다.")
    @Test
    public void testCanEnroll_Free() {
        SessionConstraint constraint = new SessionConstraint(200_000, 1);

        assertThat(SessionType.FREE.canEnroll(constraint, 1, 300_000)).isTrue();
    }

    @DisplayName("유료강의는 인원여유가 있어야 수강신청을 할 수 있다.")
    @Test
    public void testCanEnroll_PaidWithSameAmount() {
        SessionConstraint constraint = new SessionConstraint(200_000, 2);

        assertThat(SessionType.PAID.canEnroll(constraint, 1, 200_000)).isFalse();
    }

    @DisplayName("유료강의는 요금조건이 맞아야 수강신청을 할 수 있다.")
    @Test
    public void testCanEnroll_PaidWit() {
        SessionConstraint constraint = new SessionConstraint(200_000, 1);

        assertThat(SessionType.PAID.canEnroll(constraint, 0, 200_001)).isFalse();
    }
}
