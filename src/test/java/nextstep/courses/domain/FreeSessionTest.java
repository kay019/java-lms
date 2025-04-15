package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import nextstep.payments.domain.PaymentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FreeSessionTest {

    @Test
    @DisplayName("무료 강의는 참여할 수 있다.")
    void freeSessionMustBeParticipant() {
        Session session = new FreeSession(ImageTest.DEFAULT_IMAGE, new Period(LocalDate.now(), LocalDate.now()));
        session.openEnrollment();
        session.enroll(1L, PaymentTest.DEFAULT_PAYMENT);
        assertThat(session.isParticipant(1L)).isTrue();
    }
}