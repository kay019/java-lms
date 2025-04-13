package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class ImageTest {

  @Test
  void testCreateImageWithInvalidType() {
    assertThatThrownBy(() -> new Image(0L , "test.txt", 1000L, 300, 200))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("이미지 형식이 유효하지 않습니다.");
  }

  @Test
  void testCreateImageWithInvalidSize() {
    assertThatThrownBy(() -> new Image(0L , "test.jpg", 1000001L, 300, 200))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("파일 크기가 너무 큽니다.");
  }

  @Test
  void testCreateImageWithInvalidWidth() {
    assertThatThrownBy(() -> new Image(0L , "test.jpg", 1000L, 299, 200))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("가로, 세로 크기가 너무 작습니다.");
  }

  @Test
  void testCreateImageWithInvalidRatio() {
    assertThatThrownBy(() -> new Image(0L , "test.jpg", 1000L, 300, 201))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("가로 세로 비율이 유효하지 않습니다.");
  }
}