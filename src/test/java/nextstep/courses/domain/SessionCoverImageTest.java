package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionCoverImageTest {
    @DisplayName("정상 이미지")
    @Test
    public void createImage() throws Exception {
        new SessionCoverImage("jpg", 300, 200, 1024*1024);
    }

    @DisplayName("유효하지 않은 ImageType")
    @Test
    public void invalidImageType() throws Exception {
        Assertions.assertThatThrownBy(() -> new SessionCoverImage("", 300, 200, 1024*1024))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ImageType cannot be null or empty");

        Assertions.assertThatThrownBy(() -> new SessionCoverImage("jpg1", 300, 200, 1024*1024))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("jpg1 is not supported");
    }

    @DisplayName("유효하지 않은 이미지 width, height")
    @Test
    public void invalidImageDimension() throws Exception {
        Assertions.assertThatThrownBy(() -> new SessionCoverImage("jpg", 100, 200, 1024*1024))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("width and height must be between 300 and 200");

        Assertions.assertThatThrownBy(() -> new SessionCoverImage("jpg", 300, 300, 1024*1024))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The ratio of width to height should be 3:2.");
    }

    @DisplayName("유효하지 않은 이미지 size")
    @Test
    public void invalidImageSize() throws Exception {
        Assertions.assertThatThrownBy(() -> new SessionCoverImage("jpg", 300, 200, 1024*1024 + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Image size is too large to cover");
    }
}