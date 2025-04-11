package nextstep.courses.domain;

import static nextstep.courses.domain.SessionTest.SESSION1;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Test;

class FreeSessionTypeTest {

    @Test
    void 수강신청() {
        SessionType sessionType = new FreeSessionType();
        assertThatNoException().isThrownBy(() ->
                sessionType.enroll(new Payment(), SESSION1)
        );
    }

}
