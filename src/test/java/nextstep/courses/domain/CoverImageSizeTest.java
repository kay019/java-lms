package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CoverImageSizeTest {

    @Test
    @DisplayName("CoverImageSize 생성 성공 - 3:2 비율, 최소 크기 이상")
    void createCoverImageSize_success() {
        // given
        int width = 600;
        int height = 400;

        // when & then
        assertThatCode(() -> new CoverImageSize(width, height))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("CoverImageSize 생성 실패 - 최소 크기 미만")
    void createCoverImageSize_fail_minSize() {
        // given
        int width = 200;
        int height = 100;

        // when & then
        assertThatThrownBy(() -> new CoverImageSize(width, height))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("width는")
                .hasMessageContaining("hegith는");
    }

    @Test
    @DisplayName("CoverImageSize 생성 실패 - 비율이 3:2가 아님")
    void createCoverImageSize_fail_invalidRatio() {
        // given
        int width = 600;
        int height = 300;

        // when & then
        assertThatThrownBy(() -> new CoverImageSize(width, height))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("width와 height의 비율은 3:2여야합니다.");
    }
}
