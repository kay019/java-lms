package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageTest {

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
        long size = 1024 * 512; // 512KB

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
        long size = 1024 * 512; // 512KB

        // when & then
        Image image = new Image(width, height, type, size);
        assertThat(image).isNotNull();
    }

    @Test
    @DisplayName("잘못된 이미지 타입으로 이미지를 생성할 수 없다")
    void throwExceptionForInvalidImageType() {
        // given
        int width = 900;
        int height = 600;
        String type = "invalid";
        long size = 1024 * 512; // 512KB

        // when & then
        assertThatThrownBy(() -> new Image(width, height, type, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 타입은");
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
} 
