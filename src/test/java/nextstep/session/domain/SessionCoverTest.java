package nextstep.session.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.session.domain.cover.SessionCover;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionCoverTest {
    @Test
    void createTest() {
        SessionCover sessionCover = new SessionCover(1_048_576, "png", 300, 200);

        assertThat(sessionCover).isEqualTo(new SessionCover(1_048_576, "png", 300, 200));
    }

    @DisplayName("이미지의 width와 height의 비율은 3:2여야 한다.")
    @Test
    void checkRatioTest() {
        assertThatThrownBy(() -> new SessionCover(1_048_576, "png", 300, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("session cover ratio must be 3:2");
    }
}
