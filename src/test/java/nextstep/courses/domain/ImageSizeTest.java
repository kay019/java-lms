package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ImageSizeTest {

    @Test
    void 정상_이미지() {
        assertThatNoException().isThrownBy(() ->
                new ImageSize(300, 200, 1 * 1024 * 1024)
        );
    }

    @Test
    void 가로가_작은_이미지() {
        assertThatThrownBy(() ->
                new ImageSize(299, 200, 1 * 1024 * 1024)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 크기가 너무 작습니다.");
    }

    @Test
    void 세로가_작은_이미지() {
        assertThatThrownBy(() ->
                new ImageSize(300, 199, 1 * 1024 * 1024)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 크기가 너무 작습니다.");
    }

    @Test
    void 비율이_틀린_이미지() {
        assertThatThrownBy(() ->
                new ImageSize(300, 201, 1 * 1024 * 1024)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 비율이 맞지 않습니다.");
    }

    @Test
    void 너무_큰_이미지() {
        assertThatThrownBy(() ->
                new ImageSize(300, 200, 2 * 1024 * 1024)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미지의 용량이 너무 큽니다.");
    }

}
