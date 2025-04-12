package nextstep.session.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static nextstep.session.domain.image.RowTest.dummyImageRow;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RowsTest {

    public static Rows dummyRows(int width, int height) {
        List<Row> rows = IntStream.range(0, height)
            .mapToObj(i -> dummyImageRow(width))
            .collect(Collectors.toList());
        return new Rows(rows);
    }

    @DisplayName("Rows 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Rows(List.of(new Row(List.of(PixelTest.P1)))));
        assertThatThrownBy(() -> new Rows(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("null이나 빈 값을 할당할 수 없습니다.");
        assertThatThrownBy(() -> new Rows(List.of()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("null이나 빈 값을 할당할 수 없습니다.");
        assertThatThrownBy(() -> new Rows(List.of(dummyImageRow(1), dummyImageRow(2))))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("row의 크기들은 동일해야 합니다.");
    }
}
