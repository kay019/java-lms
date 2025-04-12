package nextstep.session.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PixelTest {
    public static Pixel P1 = new Pixel(1, 1, 1);

    @DisplayName("SessionImagePixel 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Pixel(100, 130, 231));
    }

    @DisplayName("SessionImagePixel 인스턴스 생성 - 255 초과인 값 들어올 시 예외 던짐")
    @Test
    public void testConstructor_255over() {
        assertThatThrownBy(() -> new Pixel(100, 130, 256))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("색상 값은 255 초과인 값이 들어올 수 없습니다.");
    }

    @DisplayName("SessionImagePixel 인스턴스 생성 - 0 미만인 값 들어올 시 예외 던짐")
    @Test
    public void testConstructor_0under() {
        assertThatThrownBy(() -> new Pixel(100, 130, -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("색상 값은 0 미만인 값이 들어올 수 없습니다.");
    }
}
