package nextstep.qna.domain;

import nextstep.courses.domain.Capacity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class CapacityTest {

    @Test
    @DisplayName("수강 정원이 음수일 경우 예외가 발생한다")
    void negativeCapacity_throwsException() {
        assertThatThrownBy(() -> new Capacity(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강 정원은 0이거나 음수일 수 없습니다.");
    }

    @DisplayName("수강 정원이 초과되었는지 확인할 수 있다")
    @Test
    void isFull_returnsTrueIfFull() {
        Capacity capacity = new Capacity(3);

        assertThat(capacity.isFull(3)).isTrue(); // 꽉 찼을 때
        assertThat(capacity.isFull(4)).isTrue(); // 넘었을 때
        assertThat(capacity.isFull(2)).isFalse(); // 여유 있을 때
    }
}