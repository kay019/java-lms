package nextstep.sessions;

import nextstep.payments.domain.Payment;
import nextstep.sessions.domain.*;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {
    @Test
    void testCreateSession() {
        Period sessionPeriod = new Period(
                LocalDate.of(2025, 4, 13),
                LocalDate.of(2025, 4, 14)
        );
        ImageInfo imageInfo = new ImageInfo(10^6, "png", 300, 200);
        Registration registration = new Registration(RegistrationType.FREE);
        assertThat(new Session(1L, 2L, sessionPeriod, imageInfo, SessionStatus.OPEN, registration))
                .isEqualTo(new Session(1L, 2L, sessionPeriod, imageInfo, SessionStatus.OPEN, registration));
    }

    @Test
    void testRegisterOpenSession() {
        Period sessionPeriod = new Period(LocalDate.of(2025, 4, 13), LocalDate.of(2025, 4, 14));
        ImageInfo imageInfo = new ImageInfo(10^6, "jpg", 300, 200);
        Registration registration = new Registration(RegistrationType.FREE);
        Session session = new Session(1L, 2L, sessionPeriod, imageInfo, SessionStatus.OPEN, registration);
        assertThat(session.register(NsUserTest.SANJIGI, 0L)).isEqualTo(
                new Payment("1|2", 1L, 2L, 0L)
        );
    }

    @Test
    void testRegisterReadySession() {
        Period sessionPeriod = new Period(LocalDate.of(2025, 4, 13), LocalDate.of(2025, 4, 14));
        ImageInfo imageInfo = new ImageInfo(10^6, "jpg", 300, 200);
        Registration registration = new Registration(RegistrationType.FREE);
        Session session = new Session(1L, 2L, sessionPeriod, imageInfo, SessionStatus.READY, registration);
        assertThatThrownBy(() -> session.register(NsUserTest.SANJIGI, 0L)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testRegisterClosedSession() {
        Period sessionPeriod = new Period(LocalDate.of(2025, 4, 13), LocalDate.of(2025, 4, 14));
        ImageInfo imageInfo = new ImageInfo(10^6, "jpg", 300, 200);
        Registration registration = new Registration(RegistrationType.FREE);
        Session session = new Session(1L, 2L, sessionPeriod, imageInfo, SessionStatus.CLOSED, registration);
        assertThatThrownBy(() -> session.register(NsUserTest.SANJIGI, 0L)).isInstanceOf(IllegalStateException.class);
    }
}
