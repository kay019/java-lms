package nextstep.session.domain.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RowTest {

    public static Row dummyImageRow(int width) {
        List<Pixel> pixels = IntStream.range(0, width)
            .mapToObj(i -> PixelTest.P1)
            .collect(Collectors.toList());
        return new Row(pixels);
    }

    @DisplayName("ImageLine 인스턴스 생성")
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Row(List.of(PixelTest.P1)));
    }
}
