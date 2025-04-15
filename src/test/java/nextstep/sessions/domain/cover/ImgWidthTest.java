package nextstep.sessions.domain.cover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImgWidthTest {
    @Test
    void createTest() {
        ImgWidth imgWidth = new ImgWidth(1042);

        assertThat(imgWidth).isEqualTo(new ImgWidth(1042));
    }

    @DisplayName("이미지의 width는 300픽셀 이상이어야 한다.")
    @Test
    void throwExceptionTest() {
        assertThatThrownBy(() -> new ImgWidth(299)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("img width must be greater than 300px");
    }
}
