package nextstep.courses.domain;

import nextstep.courses.InvalidNaturalNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositiveNumberTest {
    @DisplayName("음수는 생성 불가능")
    @Test
    public void create_positive_number() {
        assertThatThrownBy(() -> new PositiveNumber(-1L))
                .isInstanceOf(InvalidNaturalNumberException.class);
    }
}
