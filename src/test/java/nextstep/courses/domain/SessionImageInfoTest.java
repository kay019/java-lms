package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SessionImageInfoTest {

  @Test
  void 이미지_크기_초과_테스트() {
    assertThatThrownBy(() -> new SessionImageInfo(ImageType.PNG, 1024 * 1025, 300, 200))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 이미지_크기_및_비율_검사() {
    assertThatThrownBy(() -> new SessionImageInfo(ImageType.PNG, 500_000, 299, 200))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> new SessionImageInfo(ImageType.PNG, 500_000, 300, 199))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> new SessionImageInfo(ImageType.PNG, 500_000, 320, 200))
        .isInstanceOf(IllegalArgumentException.class);
  }

}