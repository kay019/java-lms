package nextstep.qna.domain;

import nextstep.courses.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class ImageTest {
    @Test
    @DisplayName("이미지 크기는 1MB를 넘을 수 없다")
    void imageSize() {
        assertThatThrownBy(() -> {
            new Image("test", "jpg", 1024 * 1024 + 1, 300, 200);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 크기는 1MB 이하여야 합니다.");
    }

    @Test
    @DisplayName("허용되지 않는 이미지 형식이다")
void imageType() {
        assertThatThrownBy(() -> {
            new Image("test", "exe", 1024 * 1024, 300, 200);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허용되지 않는 이미지 형식입니다.");
    }

    @Test
    @DisplayName("이미지 크기가 너무 작다")
    void imageSizeTooSmall() {
        assertThatThrownBy(() -> {
            new Image("test", "jpg", 1024 * 1024, 100, 100);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 크기가 너무 작습니다.");
    }

    @Test
    @DisplayName("이미지 비율이 맞지 않다")
    void imageRatio() {
        assertThatThrownBy(() -> {
            new Image("test", "jpg", 1024 * 1024, 300, 300);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지 비율은 3:2여야 합니다.");
    }
}
