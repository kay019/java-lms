package nextstep.courses.domain;

import nextstep.courses.session.domain.coverImages.SessionCoverImage;
import nextstep.courses.session.domain.coverImages.SessionImageType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SessionCoverImageTest {
    @ParameterizedTest
    @MethodSource("imageParams")
    void 이미지_검증_실패(double size, String type, int width, int height) {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new SessionCoverImage(1L, 1L, size, SessionImageType.from(type), width, height));
    }

    private static Stream<Arguments> imageParams() {
        return Stream.of(
                Arguments.of(2.0, "gif", 300, 200),   // 크기 초과
                Arguments.of(1.0, "gif", 200, 200),   // width 부족
                Arguments.of(1.0, "gif", 300, 100),   // height 부족
                Arguments.of(1.0, "gif", 500, 400),    // 비율 틀림
                Arguments.of(1.0, "aaa", 600, 400)    // 지원하지 않는 이미지타입
        );
    }

}
