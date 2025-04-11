package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void 정상_이미지() {
        assertThatNoException().isThrownBy(() ->
                new Image("http://daumt.jpg", 300, 200, 1 * 1024 * 1024, "jpg")
        );
    }

    @Test
    void 미지원_포맷() {
        assertThatThrownBy(() ->
                new Image("http://daumt.jpg", 300, 200, 1 * 1024 * 1024, "bmp")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 이미지 포맷입니다.");
    }

    @Test
    void 너무_큰_이미지() {
        assertThatThrownBy(() ->
                new Image("http://daumt.jpg", 300, 200, 2 * 1024 * 1024, "jpg")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 용량이 너무 큽니다.");
    }

}
