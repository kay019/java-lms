package nextstep.courses.domain.session.image;

import nextstep.stub.TestImageHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SessionImageTest {
    private static final String testImageUrl = "https://test.com";

    @DisplayName("SessionImage 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new SessionImage(
            testImageUrl,
            new TestImageHandler(300, 200, 1024L * 866L),
            SessionImageType.JPEG)
        );
    }

    @DisplayName("SessionImage 인스턴스 생성 - width와 height의 비율은 3:2 가 아니면 예외를 던짐")
    @Test
    public void testImage_throwExceptionByRatio() {
        assertThatThrownBy(() -> new SessionImage(
            testImageUrl,
            new TestImageHandler(300, 201, 1024L * 866L),
            SessionImageType.JPEG)
        )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("width와 height의 비율은 3:2 이여야 합니다.");
    }

    @DisplayName("이미지 가져오기 - 크기가 1MB를 초과하면 예외를 던짐")
    @Test
    public void testImage_throwExceptionBySize() throws IOException {
        assertThatThrownBy(() -> new SessionImage(
            testImageUrl,
            new TestImageHandler(300, 200, 1024L * 1025L),
            SessionImageType.JPEG)
        )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("크기가 1MB를 초과했습니다.");
    }
}
