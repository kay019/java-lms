package nextstep.session.domain.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionFeeTest {

    @DisplayName("SessionFee 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionFee(200_000));
    }

    @DisplayName("SessionFee 인스턴스 생성 - 1 이하의 값이 경우 예외를 던짐")
    @Test
    public void testConstructor_throwException() {
        assertThatThrownBy(() -> new SessionFee(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("강의 요금은 0원 이상이여야 합니다.");
    }
}
