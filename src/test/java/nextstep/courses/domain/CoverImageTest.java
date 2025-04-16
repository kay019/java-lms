package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.ImageFormat.PNG;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class CoverImageTest {
    public static CoverImage getCoverImage(ImageFormat imageFormat) {
        return new CoverImage(1024 * 512, PNG, 800, 600);
    }

    @Test
    @DisplayName("CoverImage 생성 성공")
    void createCoverImageSuccess() {
        long sizeInBytes = 1024 * 512; // 512KB
        ImageFormat format = PNG;
        int width = 800;
        int height = 600;

        assertThatCode(() -> new CoverImage(sizeInBytes, format, width, height))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("CoverImage 생성 실패 - 1MB 초과")
    void createCoverImageFail_sizeTooLarge() {
        long sizeInBytes = 1024 * 1024 + 1; // 1MB 초과
        ImageFormat format = PNG;
        int width = 800;
        int height = 600;

        assertThatThrownBy(() -> new CoverImage(sizeInBytes, format, width, height))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("size는 1MB 이하여야합니다.");
    }

    @Test
    @DisplayName("CoverImage 생성 실패 - 이미지 포맷 null")
    void createCoverImageFail_formatNull() {
        long sizeInBytes = 1024 * 512;
        ImageFormat format = null;
        int width = 800;
        int height = 600;

        assertThatThrownBy(() -> new CoverImage(sizeInBytes, format, width, height))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("imageType is null.");
    }
}
