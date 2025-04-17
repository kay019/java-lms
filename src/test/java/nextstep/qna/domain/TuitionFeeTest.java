package nextstep.qna.domain;

import nextstep.courses.domain.TuitionFee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TuitionFeeTest {

    @Test
    @DisplayName("수강료가 음수일 경우 예외가 발생한다")
    void negativeFee_throwsException() {
        assertThatThrownBy(() -> new TuitionFee(-1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수강료는 0 이거나 음수일 수 없습니다.");
    }
}
