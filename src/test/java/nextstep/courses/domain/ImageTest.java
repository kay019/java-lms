package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

public class ImageTest {
    @DisplayName("정상적인 이미지 생성")
    @Test
    public void create_image() {
        assertThatNoException().isThrownBy(() -> new Image(1000, ImageType.GIF, 300, 200));
    }
}
