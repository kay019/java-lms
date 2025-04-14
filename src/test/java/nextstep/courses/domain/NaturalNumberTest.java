package nextstep.courses.domain;

import nextstep.courses.InvalidNaturalNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NaturalNumberTest {
    @DisplayName("음수, 0은 생성 불가능")
    @Test
    public void create_natural_number() {
        assertThatThrownBy(() -> new NaturalNumber(0L))
                .isInstanceOf(InvalidNaturalNumberException.class);
    }
}
