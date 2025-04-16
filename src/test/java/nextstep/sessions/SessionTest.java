package nextstep.sessions;

import nextstep.payments.domain.Payment;
import nextstep.sessions.domain.*;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {
    static final Period period = new Period(
            LocalDate.of(2025, 4, 13),
            LocalDate.of(2025, 4, 14)
    );
    static final ImageInfo image = new ImageInfo(1_000, "png", 300, 200);

    @Test
    void testCreateFreeSession() {
        assertThat(new FreeSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(Set.of(NsUserTest.SANJIGI))))
                .isEqualTo(new FreeSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(Set.of(NsUserTest.SANJIGI))));
    }

    @Test
    void testCreatePaidSession() {
        assertThat(new PaidSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(Set.of(NsUserTest.SANJIGI)), 1L, 100))
                .isEqualTo(new PaidSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(Set.of(NsUserTest.SANJIGI)), 1L, 100));
    }

    @Test
    void testRegisterOpenFreeSession() {
        Session session = new FreeSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration());
        assertThat(session.register(NsUserTest.SANJIGI, 0L)).isEqualTo(
                new Payment("1|2", 1L, 2L, 0L)
        );
    }

    @Test
    void testRegisterOpenPaidSession() {
        Session session = new PaidSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(), 100L, 10);
        assertThat(session.register(NsUserTest.SANJIGI, 100L)).isEqualTo(
                new Payment("1|2", 1L, 2L, 100L)
        );
    }

    @Test
    void testRegisterReadySession() {
        Session session = new FreeSession(1L, 2L, period, image, SessionStatus.READY, new Registration());
        assertThatThrownBy(() -> session.register(NsUserTest.SANJIGI, 0L)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testRegisterClosedSession() {
        Session session = new FreeSession(1L, 2L, period, image, SessionStatus.CLOSED, new Registration());
        assertThatThrownBy(() -> session.register(NsUserTest.SANJIGI, 0L)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testPaidSessionNotEqualAmountException() {
        Session session = new PaidSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(), 100L, 10);
        assertThatThrownBy(() ->
            session.register(NsUserTest.SANJIGI, 0L)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testPaidSessionOverMaxStudentException() {
        Session session = new PaidSession(1L, 2L, period, image, SessionStatus.OPEN, new Registration(), 100L, 1);
        session.register(NsUserTest.SANJIGI, 100L);
        assertThatThrownBy(() ->
            session.register(NsUserTest.JAVAJIGI, 100L)
        ).isInstanceOf(IllegalStateException.class);
    }
}
