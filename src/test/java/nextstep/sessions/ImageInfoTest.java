package nextstep.sessions;

import nextstep.sessions.domain.ImageInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageInfoTest {
    @Test
    void testInvalidType() {
        assertThatThrownBy(() ->
            new ImageInfo(10^6, "abc", 300, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidBytes() {
        assertThatThrownBy(() ->
                new ImageInfo(10^7, "jpg", 300, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidWidth() {
        assertThatThrownBy(() ->
                new ImageInfo(10^6, "jpg", 200, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidHeight() {
        assertThatThrownBy(() ->
                new ImageInfo(10^6, "jpg", 300, 100)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidRatio() {
        assertThatThrownBy(() ->
                new ImageInfo(10^6, "jpg", 400, 200)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
