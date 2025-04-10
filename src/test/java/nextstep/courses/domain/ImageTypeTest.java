package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageTypeTest {
    @DisplayName("지원하는 확장자 이외의 이미지는 생성할 수 없습니다.")
    @Test
    public void create_image_no_extension() {
        assertThatThrownBy(() -> ImageType.of("webp"))
                .isInstanceOf(CannotUploadImageException.class)
                .hasMessage("이미지의 확장자는 gif, jpg, jpeg, png, svg만 허용합니다.");
    }
}
