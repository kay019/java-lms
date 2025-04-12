package nextstep.session.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionImageTest {
    private static final String testImageUrl = "https://test.com";

    @DisplayName("SessionImage 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionImage(testImageUrl, SessionImageType.JPEG));
    }

    @DisplayName("이미지 가져오기")
    @Test
    public void testImage() throws IOException {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage download() {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public long byteSize() {
                return 1024L * 866L;
            }
        };

        SessionImage sessionImage = new SessionImage(testImageUrl, imageHandlerStub, SessionImageType.JPEG);
        assertDoesNotThrow(sessionImage::image);
    }

    @DisplayName("이미지 가져오기 - width와 height의 비율은 3:2 가 아니면 예외를 던짐")
    @Test
    public void testImage_throwExceptionByRatio() throws IOException {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage download() {
                return new BufferedImage(300, 201, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public long byteSize() {
                return 1024L * 866L;
            }
        };

        SessionImage sessionImage = new SessionImage(testImageUrl, imageHandlerStub, SessionImageType.JPEG);
        assertThatThrownBy(sessionImage::image)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("width와 height의 비율은 3:2 이여야 합니다.");
    }

    @DisplayName("이미지 가져오기 - 크기가 1MB를 초과하면 예외를 던짐")
    @Test
    public void testImage_throwExceptionBySize() throws IOException {
        ImageHandler imageHandlerStub = new ImageHandler() {
            @Override
            public BufferedImage download() {
                return new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
            }

            @Override
            public long byteSize() {
                return 1024L * 1025L;
            }
        };

        SessionImage sessionImage = new SessionImage(testImageUrl, imageHandlerStub, SessionImageType.JPEG);
        assertThatThrownBy(sessionImage::image)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("크기가 1MB를 초과했습니다.");
    }
}
