package nextstep.courses.domain;

import nextstep.courses.session.domain.coverImages.SessionCoverImages;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SessionCoverImagesTest {
    @Test
    void 이미지가_없으면_예외() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionCoverImages(List.of()));
    }
}
