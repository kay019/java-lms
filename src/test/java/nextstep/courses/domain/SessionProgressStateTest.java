package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionProgressStateTest {
    @DisplayName("강의 상태가 모집 중일 때만 수강신청 가능")
    @Test
    public void can_register_session_state() {
        assertThat(SessionRecruitmentState.canNotRegister(SessionRecruitmentState.RECRUTING)).isFalse();
    }
}
