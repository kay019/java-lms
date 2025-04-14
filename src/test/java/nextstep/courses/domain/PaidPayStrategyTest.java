package nextstep.courses.domain;

import nextstep.courses.CannotRegisterException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaidPayStrategyTest {
    @DisplayName("유료 정책에서는 강의 가격과 결제 가격이 같지 않으면 결제 불가능")
    @Test
    public void cannot_pay_different_price() {
        assertThatThrownBy(() -> new PaidPayStrategy(1000L, 5L).pay(NsUserTest.JAVAJIGI, 0L, 1, new NaturalNumber(500L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("결제 금액이 강의의 가격과 같지 않습니다.");
    }

    @DisplayName("유료 정책에서는 수강 정원이 초과되면 등록 불가능")
    @Test
    public void cannot_pay_over_capacity() {
        assertThatThrownBy(() -> new PaidPayStrategy(1000L, 5L).pay(NsUserTest.JAVAJIGI, 0L, 5, new NaturalNumber(1000L)))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("해당 강의의 수강 정원이 모두 마감되었습니다.");
    }
}
