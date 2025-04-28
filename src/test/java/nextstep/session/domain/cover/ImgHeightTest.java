package nextstep.session.domain.cover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImgHeightTest {
    @Test
    void createTest() {
        ImgHeight imgHeight = new ImgHeight(1042);

        assertThat(imgHeight).isEqualTo(new ImgHeight(1042));
    }

    @DisplayName("이미지의 height는 200픽셀 이상이어야 한다.")
    @Test
    void throwExceptionTest() {
        assertThatThrownBy(() -> new ImgHeight(199)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("img height must be greater than 200px");
    }
}
