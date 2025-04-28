package nextstep.session.domain.cover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImgSizeTest {
    @Test
    void createTest() {
        ImgSize imgSize = new ImgSize(1042);

        assertThat(imgSize).isEqualTo(new ImgSize(1042));
    }

    @DisplayName("이미지 크기가 1MB 초과하면 예외가 발생한다.")
    @Test
    void sizeExceptionTest() {
        assertThatThrownBy(() -> new ImgSize(1048576 + 1)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("img size must be less than 1MB");
    }
}
