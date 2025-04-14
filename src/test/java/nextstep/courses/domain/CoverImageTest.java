package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static nextstep.courses.domain.ImageType.*;

class CoverImageTest {

    @Test
    @DisplayName("이미지 크기가 1MB를 초과하면 에러를 던진다.")
    void imageSizeTest() {
        // given
        long imageSize = 1_048_577; // 1MB + 1byte
        ImageType imageType = ImageType.SVG;
        long width = 300;
        long height = 200;


        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverImage(imageSize, imageType, width, height));
    }

    @Test
    @DisplayName("커버 이미지를 정상적으로 생성한다.")
    void imageTest() {
        // given
        long imageSize = 1_048_576; // 1MB
        ImageType imageType = ImageType.SVG;
        long width = 300;
        long height = 200;


        // when
        CoverImage coverImage = new CoverImage(imageSize, imageType, width, height);

        // & then
        Assertions.assertThat(coverImage)
                .extracting("imageSize", "imageType", "width", "height")
                .containsExactly(imageSize, imageType, width, height);
    }

    @ParameterizedTest
    @DisplayName("gif, jpg(jpeg 포함), png, svg외 이미지 타입만 허용한다.")
    @MethodSource("provideValidImageTypes")
    void imageTypeTest(ImageType imageType) {
        // given
        long imageSize = 1_048_576; // 1MB
        long width = 300;
        long height = 200;


        // when
        CoverImage coverImage = new CoverImage(imageSize, imageType, width, height);

        // & then
        Assertions.assertThat(coverImage)
                .extracting("imageSize", "imageType", "width", "height")
                .containsExactly(imageSize, imageType, width, height);
    }

    static Stream<ImageType> provideValidImageTypes() {
        return Stream.of(SVG, GIF, JPG, JPEG, PNG);
    }

    @ParameterizedTest
    @CsvSource({
            "299, 200, ", // 너비 부족
            "300, 199", // 높이 부족
            "400, 200"  // 비율 틀림
    })
    void invalidSizeCombination(long width, long height) {
        Assertions.assertThatThrownBy(() -> new CoverImage(1_000_000, ImageType.SVG, width, height))
                .isInstanceOf(IllegalArgumentException.class);
    }
}