package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ImageFormatTest {

    @Test
    void 타입_확인() {
        assertThat(ImageFormat.findFormat("jpg")).isEqualTo(ImageFormat.JPEG);
        assertThat(ImageFormat.findFormat("jpeg")).isEqualTo(ImageFormat.JPEG);
        assertThat(ImageFormat.findFormat("png")).isEqualTo(ImageFormat.PNG);
        assertThat(ImageFormat.findFormat("gif")).isEqualTo(ImageFormat.GIF);
        assertThat(ImageFormat.findFormat("bmp")).isEqualTo(ImageFormat.NOT_SUPPORTED);
        assertThat(ImageFormat.findFormat("tiff")).isEqualTo(ImageFormat.NOT_SUPPORTED);
    }

}
