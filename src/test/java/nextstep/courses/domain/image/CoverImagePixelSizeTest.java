package nextstep.courses.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CoverImagePixelSizeTest {
    static final int MIN_WIDTH = 300;
    static final int MIN_HEIGHT = 200;

    @Test
    @DisplayName("width가 300보다 작으면 예외를 반환한다.")
    void validatePixelSize_widthLT300() {
        int width = MIN_WIDTH - 10;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverImagePixelSize(width, MIN_HEIGHT));
    }

    @Test
    @DisplayName("height가 200보다 작으면 예외를 반환한다.")
    void validatePixelSize_heightLT200() {
        int height = MIN_HEIGHT - 10;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverImagePixelSize(MIN_WIDTH, height));
    }

    @Test
    @DisplayName("width과 height의 비율이 3:2가 아니면 예외를 반환한다.")
    void validateRatioTest() {
        int width = 5 * 100;
        int height = 3 * 100;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverImagePixelSize(width, height));
    }
}
