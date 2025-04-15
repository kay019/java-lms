package nextstep.courses.domain.session.constraint;

import nextstep.courses.domain.session.constraint.SessionCapacity;
import nextstep.courses.domain.session.constraint.SessionConstraint;
import nextstep.courses.domain.session.constraint.SessionFee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionConstraintTest {

    @DisplayName("SessionPayments 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionConstraint(new SessionFee(800_000), new SessionCapacity(80)));
    }

    @DisplayName("최대 수강 인원이 찼는지 확인")
    @Test
    public void testIsFull() {
        SessionConstraint sessionConstraint = new SessionConstraint(800_000, 1);

        assertThat(sessionConstraint.isGreaterThenCapacity(2)).isTrue();
    }

    @DisplayName("수강료와 지불 요금 금액이 맞는지 확인")
    @Test
    public void testMatchesFee() {
        SessionConstraint sessionConstraint = new SessionConstraint(200_000, 1);

        assertAll(
            () -> assertThat(sessionConstraint.isSameFee(200_000)).isTrue(),
            () -> assertThat(sessionConstraint.isSameFee(200_001)).isFalse()
        );
    }
}
