package nextstep.session.domain.cover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImgTypeTest {
    @Test
    void fromTest() {
        ImgType imgType = ImgType.from("gif");

        assertThat(imgType).isEqualTo(ImgType.from("gif"));
    }

    @DisplayName("이미지 타입은 gif, jpg, png, svg만 허용한다.")
    @Test
    void fromExceptionTest() {
        assertThatThrownBy(() -> ImgType.from("mp4")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 이미지 타입입니다: mp4");
    }

    @Test
    void getContentTypeTest() {
        assertThat(ImgType.GIF.getContentType()).isEqualTo("image/gif");
    }
}
