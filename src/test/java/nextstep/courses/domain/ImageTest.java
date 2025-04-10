package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageTest {
    @DisplayName("정상적인 이미지 생성")
    @Test
    public void create_image() {
        assertThatNoException().isThrownBy(() -> new Image(1000, ImageType.GIF, 300, 200));
    }

    @DisplayName("크기가 1MB 초과인 이미지는 생성할 수 없습니다.")
    @Test
    public void create_image_over_1MB() {
        assertThatThrownBy(() -> new Image(1001, ImageType.GIF, 300, 200))
                .isInstanceOf(CannotUploadImageException.class)
                .hasMessage("이미지의 크기는 1MB 이하여야 합니다.");
    }

    @DisplayName("width가 300픽셀 미만이거나 height가 200픽셀 미만인 이미지는 생성할 수 없습니다.")
    @Test
    public void create_image_below_width_height() {
        assertThatThrownBy(() -> new Image(1000, ImageType.GIF, 297, 198))
                .isInstanceOf(CannotUploadImageException.class)
                .hasMessage("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
    }

    @DisplayName("width와 height 비율이 3:2가 아닌 이미지는 생성할 수 없습니다.")
    @Test
    public void create_image_width_height_ratio() {
        assertThatThrownBy(() -> new Image(1000, ImageType.GIF, 300, 205))
                .isInstanceOf(CannotUploadImageException.class)
                .hasMessage("이미지의 width와 height의 비율은 3:2여야 합니다.");
    }
}
