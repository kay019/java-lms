package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageTest {
    public static final Image DEFAULT_IMAGE = new Image("https://example.com/image.jpg", 1000, 1000, "jpg", 1024 * 1024);

    @Test
    @DisplayName("이미지 생성")
    void createImage() {
        Image image = new Image("https://example.com/image.jpg", 1000, 1000, "jpg", 1024 * 1024);
        assertThat(image).isNotNull();
    }

    @Test
    @DisplayName("이미지는 최대 크기를 초과할 수 없다.")
    void imageSizeMustBeLessThanMaxSize() {
        assertThatThrownBy(() -> new Image("https://example.com/image.jpg", 1000, 1000, "jpg", 1024 * 1024 + 1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지는 지원 형식을 가져야 한다.")
    void imageFormatMustBeSupported() {
        assertThatThrownBy(() -> new Image("https://example.com/image.jpg", 1000, 1000, "webp", 1024 * 1024))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지는 최소 해상도를 가져야 한다.")
    void imageResolutionMustBeGreaterThanMinResolution() {
        assertThatThrownBy(() -> new Image("https://example.com/image.jpg", 299, 200, "jpg", 1024 * 1024))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Image("https://example.com/image.jpg", 300, 199, "jpg", 1024 * 1024))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지는 너비:높이 = 3:2 비율을 가져야 한다.")
    void imageRatioMustBe3To2() {
        assertThatThrownBy(() -> new Image("https://example.com/image.jpg", 300, 200, "jpg", 1024 * 1024))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Image("https://example.com/image.jpg", 300, 201, "jpg", 1024 * 1024))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지는 URL이 필수 값이어야 한다.")
    void imageUrlMustBeRequired() {
        assertThatThrownBy(() -> new Image(null, 300, 200, "jpg", 1024 * 1024))
            .isInstanceOf(IllegalArgumentException.class);
    }

}