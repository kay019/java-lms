package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageTest {

    @Test
    @DisplayName("올바른 이미지 타입으로 이미지를 생성할 수 있다")
    void createImageWithValidType() {
        // given
        int width = 900;
        int height = 600;
        String type = "png";
        long size = 1024 * 512;

        // when & then
        Image image = new Image(width, height, type, size);
        assertThat(image).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"gif", "jpg", "jpeg", "png", "svg"})
    @DisplayName("다양한 유효한 이미지 타입으로 이미지를 생성할 수 있다")
    void createImageWithVariousValidTypes(String type) {
        // given
        int width = 900;
        int height = 600;
        long size = 1024 * 512;

        // when & then
        Image image = new Image(width, height, type, size);
        assertThat(image).isNotNull();
    }

    @Test
    @DisplayName("대소문자 구분 없이 이미지 타입을 처리한다")
    void handleImageTypeCaseInsensitive() {
        // given
        int width = 900;
        int height = 600;
        String type = "PNG";
        long size = 1024 * 512;

        // when & then
        Image image = new Image(width, height, type, size);
        assertThat(image).isNotNull();
    }

    @Test
    @DisplayName("null 이미지 타입으로 이미지를 생성할 수 없다")
    void throwExceptionForNullImageType() {
        // given
        int width = 900;
        int height = 600;
        String type = null;
        long size = 1024 * 512;

        // when & then
        assertThatThrownBy(() -> new Image(width, height, type, size))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미지 크기가 1MB를 초과하면 예외 발생")
    public void failWhenSizeTooLarge() {
        // given
        int width = 600;
        int height = 400;
        String type = "png";
        long size = 1024 * 1024 + 1;

        // when & then
        assertThatThrownBy(() -> new Image(width, height, type, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 크기는 1MB 이하여야 합니다");
    }

    @ParameterizedTest
    @DisplayName("이미지 최소 크기 미달 시 예외 발생")
    @CsvSource({
            "299, 200",
            "300, 199",
            "150, 100"
    })
    public void failWhenTooSmall(int width, int height) {
        // given
        String type = "jpg";
        long size = 100 * 1024;

        // when & then
        assertThatThrownBy(() -> new Image(width, height, type, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 크기는 최소 300x200 픽셀 이상이어야 합니다");
    }

    @ParameterizedTest
    @DisplayName("이미지 비율이 3:2가 아닌 경우 예외 발생")
    @CsvSource({
            "400, 200",
            "600, 500",
            "900, 500"
    })
    public void failWhenInvalidRatio(int width, int height) {
        // given
        String type = "jpg";
        long size = 100 * 1024;

        // when & then
        assertThatThrownBy(() -> new Image(width, height, type, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 가로와 세로 비율은 3:2여야 합니다");
    }

    @Test
    @DisplayName("지원하지 않는 이미지 타입 예외 발생")
    public void failWhenInvalidImageType() {
        // given
        int width = 600;
        int height = 400;
        String type = "bmp";
        long size = 100 * 1024;

        // when & then
        assertThatThrownBy(() -> new Image(width, height, type, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 타입은 gif, jpg, jpeg, png, svg 중 하나여야 합니다");
    }
} 
