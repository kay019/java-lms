package nextstep.courses.domain;

import nextstep.courses.session.domain.SessionStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionStatusTest {
    @Test
    void 모집중_상태가_아님() {
        Assertions.assertThat(SessionStatus.CLOSE.canNotEnroll()).isTrue();
    }

    @Test
    void 모집중() {
        Assertions.assertThat(SessionStatus.PREPARING.canNotEnroll()).isFalse();
        Assertions.assertThat(SessionStatus.OPEN.canNotEnroll()).isFalse();
    }
}
