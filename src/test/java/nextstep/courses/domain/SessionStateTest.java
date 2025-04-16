package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionStateTest {
    @DisplayName("강의 상태가 모집 중일 때만 수강신청 가능")
    @Test
    public void create_image() {
        assertThat(SessionState.canNotRegister(SessionState.RECRUTING)).isFalse();
    }
}
