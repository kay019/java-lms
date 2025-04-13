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
}
