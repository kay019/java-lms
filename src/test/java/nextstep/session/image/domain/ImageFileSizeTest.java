package nextstep.session.image.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.exception.ImageFileSizeIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageFileSizeTest {
    @Test
    @DisplayName("정상적인 파일 크기 생성 - 1바이트")
    void createWithMinSize() {
        ImageFileSize size = new ImageFileSize(1);
        ImageFileSize expectedSize = new ImageFileSize(1);

        assertThat(size).isEqualTo(expectedSize);
    }

    @Test
    @DisplayName("정상적인 파일 크기 생성 - 최대값(1MB)")
    void createWithMaxSize() {
        ImageFileSize size = new ImageFileSize(1024 * 1024);
        ImageFileSize expectedSize = new ImageFileSize(1024 * 1024);

        assertThat(size).isEqualTo(expectedSize);
    }

    @Test
    @DisplayName("파일 크기가 0 이하일 때 예외 발생")
    void createWithZeroOrNegativeSize() {
        assertThatThrownBy(() -> new ImageFileSize(-1))
            .isInstanceOf(ImageFileSizeIllegalArgumentException.class);
    }

    @Test
    @DisplayName("파일 크기가 1MB를 초과하면 예외 발생")
    void createWithOverMaxSize() {
        assertThatThrownBy(() -> new ImageFileSize(1024 * 1024 + 1))
            .isInstanceOf(ImageFileSizeIllegalArgumentException.class);
    }
}
