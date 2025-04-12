package nextstep.session.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class PixelTest {
    public static Pixel P1 = new Pixel(1,1,1);

    @DisplayName("SessionImagePixel 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Pixel(100,130,231));
        assertThatThrownBy(() -> new Pixel(100,130,256))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("색상 값은 255 초과인 값이 들어올 수 없습니다.");
        assertThatThrownBy(() -> new Pixel(100,130,-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("색상 값은 0 미만인 값이 들어올 수 없습니다.");
    }

    @DisplayName("Byte 크기 구하기")
    @Test
    public void testByteSize() {
        int r = 255;
        int g = 255;
        int b = 255;
        Pixel pixel = new Pixel(r, g, b);
        assertThat(pixel.byteSize()).isEqualTo(165_813_75);
    }
}
