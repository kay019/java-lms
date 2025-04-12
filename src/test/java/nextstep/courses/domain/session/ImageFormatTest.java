package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ImageFormatTest {

    @ParameterizedTest
    @CsvSource({
            "jpg, JPEG",
            "jpeg, JPEG",
            "png, PNG",
            "gif, GIF",
            "bmp, NOT_SUPPORTED",
            "tiff, NOT_SUPPORTED"
    })
    void 타입_확인(String format, ImageFormat expectedFormat) {
        assertThat(ImageFormat.findFormat(format)).isEqualTo(expectedFormat);
    }

}
