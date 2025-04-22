package nextstep.courses.domain.session.inner;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import nextstep.courses.domain.session.Session;
import org.junit.jupiter.api.Test;

public class CoverImageTest {
    public static final CoverImage COVER_IMAGE1 = new CoverImage(1L, 1L, "http://daumt.jpg", 300, 200, 1 * 1024 * 1024, "jpg");

    @Test
    void 정상_이미지() {
        assertThatNoException().isThrownBy(() ->
                new CoverImage(1L, 1L, "http://daumt.jpg", 300, 200, 1 * 1024 * 1024, "jpg")
        );
    }

    @Test
    void 미지원_포맷() {
        assertThatThrownBy(() ->
                new CoverImage(1L, 1L, "http://daumt.jpg", 300, 200, 1 * 1024 * 1024, "bmp")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 이미지 포맷입니다.");
    }

}
