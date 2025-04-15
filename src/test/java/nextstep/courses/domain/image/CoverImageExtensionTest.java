package nextstep.courses.domain.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CoverImageExtensionTest {
    @ParameterizedTest
    @DisplayName("올바른 확장자 Enum을 반환한다.")
    @CsvSource({
            "gif, GIF", "GIF, GIF",
            "jpg, JPG", "jPG, JPG",
            "jpeg, JPEG", "JPEG, JPEG",
            "png, PNG", "pnG, PNG",
            "svg, SVG", "SVG, SVG"
    })
    void fromTest(String input, CoverImageExtension expected) {
        CoverImageExtension result = CoverImageExtension.from(input);

        Assertions.assertEquals(result, expected);
    }

    @Test
    @DisplayName("존재하지 않는 확장자이면 예외를 반환한다.")
    void fromTest_notExistExtension() {
        String input = "pdf";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> CoverImageExtension.from(input));
    }

}
