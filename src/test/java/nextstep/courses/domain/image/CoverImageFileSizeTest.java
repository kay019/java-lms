package nextstep.courses.domain.image;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoverImageFileSizeTest {
    @Test
    @DisplayName("이미지 크기가 1MB 보다 크면 예외를 반환한다.")
    void validateTest() {
        long size = 2 * 1024 * 1024;

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new CoverImageFileSize(size));
    }

}
