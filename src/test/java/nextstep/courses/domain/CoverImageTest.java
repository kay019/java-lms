package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CoverImageTest {
    @Test
    void 이미지생성테스트_성공() {
        assertDoesNotThrow(() -> new CoverImage(1024 * 1024, "JPEG", 300, 200));
        assertDoesNotThrow(() -> new CoverImage(10 * 1024, "JPG", 900, 600));
        assertDoesNotThrow(() -> new CoverImage(1024, "PNG", 330, 220));
    }

    @Test
    void 이미지생성테스트_실패_사이즈초과() {
        assertThatThrownBy(() -> new CoverImage(1024 * 1024 + 1, "JPEG", 300, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지의 용량은 1MB를 넘을 수 없습니다.");
    }

    @Test
    void 이미지생성테스트_실패_지원하지_않는_타입() {
        assertThatThrownBy(() -> new CoverImage(1024 * 1024, "PNG-24", 300, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 이미지 타입입니다.");
        assertThatThrownBy(() -> new CoverImage(1024 * 1024, "WEBP", 300, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지원하지 않는 이미지 타입입니다.");
    }

    @Test
    void 이미지생성테스트_실패_적절하지_않은_해상도() {
        assertThatThrownBy(() -> new CoverImage(1024 * 1024, "JPEG", 299, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 크기가 적절하지 않습니다.");
        assertThatThrownBy(() -> new CoverImage(1024 * 1024, "JPEG", 350, 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미지 크기가 적절하지 않습니다.");
    }
}