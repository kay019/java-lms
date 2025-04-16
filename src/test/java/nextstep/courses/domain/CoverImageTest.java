package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoverImageTest {
    @Test
    public void coverImage_이미지크기는1MB이하() {
        Assertions.assertThatThrownBy(() -> {
            new CoverImage("gif" , 1024 * 1024 * 2, 300, 200);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void coverImage_이미지타입() {
        Assertions.assertThatThrownBy(() -> {
            new CoverImage("abc" , 1024 * 1024, 300, 200);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void coverImage_width() {
        Assertions.assertThatThrownBy(() -> {
            new CoverImage("gif" , 1024 * 1024, 200, 200);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void coverImage_height() {
        Assertions.assertThatThrownBy(() -> {
            new CoverImage("gif" , 1024 * 1024, 300, 100);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void coverImage_width와height비율() {
        Assertions.assertThatThrownBy(() -> {
            new CoverImage("gif" , 1024 * 1024, 400, 300);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
