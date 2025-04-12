package nextstep.session.domain.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionCapacityTest {

    @DisplayName("SessionCapacity 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionCapacity(4));
    }

    @DisplayName("SessionCapacity 인스턴스 생성 - 0 이하의 경우 예외 던짐")
    @Test
    public void testConstructor_throwException() {
        assertThatThrownBy(() -> new SessionCapacity(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("수강 인원은 1 이상이여야 합니다.");
    }
}
