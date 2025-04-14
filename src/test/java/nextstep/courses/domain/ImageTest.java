package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class ImageTest {

  @Test
  void 이미지생성_유효하지않은타입_예외발생() {
    assertThatThrownBy(() -> new Image("test.txt", 1000L, 300, 200))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("이미지 형식이 유효하지 않습니다.");
  }

  @Test
  void 이미지생성_파일크기초과_예외발생() {
    assertThatThrownBy(() -> new Image("test.jpg", 1000001L, 300, 200))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("파일 크기가 너무 큽니다.");
  }

  @Test
  void 이미지생성_가로조건_예외발생() {
    assertThatThrownBy(() -> new Image("test.jpg", 1000L, 299, 200))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("가로, 세로 크기가 너무 작습니다.");
  }

  @Test
  void 이미지생성_비율불일치_예외발생() {
    assertThatThrownBy(() -> new Image("test.jpg", 1000L, 300, 201))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("가로 세로 비율이 유효하지 않습니다.");
  }
}