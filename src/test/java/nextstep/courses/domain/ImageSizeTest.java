package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.exception.ImageSizeIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageSizeTest {
    @Test
    @DisplayName("정상적인 이미지 크기 생성 (비율 3:2, 최소 크기 이상)")
    void createWithValidSize() {
        ImageSize imageSize = new ImageSize(300, 200);

        assertThat(imageSize.getWidth()).isEqualTo(300);
        assertThat(imageSize.getHeight()).isEqualTo(200);
    }

    @Test
    @DisplayName("너비가 최소값 미만이면 예외 발생")
    void createWithWidthBelowMin() {
        assertThatThrownBy(() -> new ImageSize(299, 200))
            .isInstanceOf(ImageSizeIllegalArgumentException.class);
    }

    @Test
    @DisplayName("높이가 최소값 미만이면 예외 발생")
    void createWithHeightBelowMin() {
        assertThatThrownBy(() -> new ImageSize(300, 199))
            .isInstanceOf(ImageSizeIllegalArgumentException.class);
    }

    @Test
    @DisplayName("비율이 3:2가 아니면 예외 발생")
    void createWithInvalidAspectRatio() {
        assertThatThrownBy(() -> new ImageSize(300, 400))
            .isInstanceOf(ImageSizeIllegalArgumentException.class);
    }
}
