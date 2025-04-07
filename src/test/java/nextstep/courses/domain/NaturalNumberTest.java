package nextstep.courses.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;


class NaturalNumberTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void _0을_포함한_자연수가_아닐_경우_예외가_발생한다(int number) {
        IllegalArgumentException e
            = catchIllegalArgumentException(() -> new NaturalNumber(number));

        assertThat(e).hasMessageContaining("0을 포함한 자연수만 허용 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void _0을_포함한_자연수만_생성이_가능하다(int number) {
        new NaturalNumber(number);
    }

}
