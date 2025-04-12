package nextstep.session.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.session.domain.image.RowsTest.dummyRows;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ImageTest {

    @DisplayName("SessionImage 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Image(dummyRows(300, 200), Type.GIF));
        assertThatThrownBy(() -> new Image(dummyRows(300, 201), Type.GIF))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("width와 height의 비율은 3:2 이여야 합니다.");
        assertThatThrownBy(() -> new Image(dummyRows(726, 484), Type.GIF))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("크기가 1MB를 초과했습니다.");
    }
}
