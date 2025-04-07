package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.catchIllegalArgumentException;

class CoverImageResolutionTest {

    @ParameterizedTest
    @CsvSource(value = {
        "1, 200",
        "300, 1",
        "1, 1"})
    void 이미지의_width는_300픽셀_height는_200픽셀_이상이어야_합니다(int width, int height) {
        IllegalArgumentException e
            = catchIllegalArgumentException(() -> new CoverImageResolution(width, height));
        Assertions.assertThat(e).hasMessage("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
        "300, 201",
        "301, 200",
        "500, 1000"})
    void 이미지의_width와_height의_비율은_3대2여야_합니다(int width, int height) {
        IllegalArgumentException e
            = catchIllegalArgumentException(() -> new CoverImageResolution(width, height));
        Assertions.assertThat(e).hasMessage("width와 height의 비율은 3:2여야 합니다.");
    }

}
