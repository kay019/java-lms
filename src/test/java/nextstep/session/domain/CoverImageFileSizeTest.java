package nextstep.session.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchIllegalArgumentException;

class CoverImageFileSizeTest {

    @Test
    void 사이즈가_1MB가_초과되면_예외가_발생한다() {
        IllegalArgumentException e = catchIllegalArgumentException(
            () -> new CoverImageFileSize(1024 * 1024 + 1));

        Assertions.assertThat(e).hasMessageContaining("이미지 크기는 0보다 크고 1MB 이하여야 합니다.");
    }

    @Test
    void 사이즈가_0보다_작으면_예외가_발생한다() {
        IllegalArgumentException e = catchIllegalArgumentException(
            () -> new CoverImageFileSize(-1));

        Assertions.assertThat(e).hasMessageContaining("이미지 크기는 0보다 크고 1MB 이하여야 합니다.");
    }

}
