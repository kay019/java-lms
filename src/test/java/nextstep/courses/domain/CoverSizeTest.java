package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoverSizeTest {

    @Test
    @DisplayName("커버사이즈의 가로 길이가 300보다 작으면 예외발생")
    void coverSizeWidthLessThan300() {
        // given
        int width = 299;
        int height = 200;

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverSize(width, height))
                .withMessage("커버 사이즈는 최소 300x200 입니다.");
    }

    @Test
    @DisplayName("커버사이즈의 세로 길이가 200보다 작으면 예외발생")
    void coverSizeHeightLessThan200() {
        // given
        int width = 300;
        int height = 199;

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverSize(width, height))
                .withMessage("커버 사이즈는 최소 300x200 입니다.");
    }

    @Test
    @DisplayName("커버사이즈의 비율이 3:2가 아니면 예외발생")
    void coverSizeRatioNot3to2() {
        // given
        int width = 300;
        int height = 201;

        Assertions.assertThatThrownBy(() -> new CoverSize(width, height))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커버 사이즈는 3:2 비율이어야 합니다.");
    }

    @Test
    @DisplayName("커버사이즈의 비율이 3:2이면 정상적으로 생성된다.")
    void coverSizeRatio3to2() {
        // given
        int width = 300;
        int height = 200;

        // when
        CoverSize coverSize = new CoverSize(width, height);

        // then
        Assertions.assertThat(coverSize).isNotNull();
    }


}
