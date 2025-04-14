package nextstep.sessions;

import nextstep.sessions.domain.ImageInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageInfoTest {
    @ParameterizedTest
    @CsvSource(value = {"100,jpg,300,200", "1_000,png,600,400", "10_000,gif,750,500", "1_000_000,svg,1200,800"})
    void testCreateImageInfo(int bytes, String type, int width, int height) {
        new ImageInfo(bytes, type, width, height);
    }

    @Test
    void testInvalidType() {
        assertThatThrownBy(() ->
            new ImageInfo(1_000_000, "abc", 300, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidBytes() {
        assertThatThrownBy(() ->
                new ImageInfo(10_000_000, "jpg", 300, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidWidth() {
        assertThatThrownBy(() ->
                new ImageInfo(1_000_000, "jpg", 200, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidHeight() {
        assertThatThrownBy(() ->
                new ImageInfo(1_000_000, "jpg", 300, 100)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidRatio() {
        assertThatThrownBy(() ->
                new ImageInfo(1_000_000, "jpg", 400, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
