package nextstep.sessions;

import nextstep.sessions.domain.Registration;
import nextstep.sessions.domain.RegistrationType;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegistrationTest {
    @Test
    void testFreeRegistration() {
        Registration registration = new Registration(RegistrationType.FREE);
        registration.register(NsUserTest.JAVAJIGI, 0L);
        registration.register(NsUserTest.SANJIGI, 0L);
    }

    @Test
    void testOverMaxStudentException() {
        Registration registration = new Registration(RegistrationType.PAID, 100L, 1);
        registration.register(NsUserTest.JAVAJIGI, 100L);
        assertThatThrownBy(() ->
            registration.register(NsUserTest.SANJIGI, 100L)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testNotEqualFeeException() {
        Registration registration = new Registration(RegistrationType.PAID, 100L, 1);
        assertThatThrownBy(() ->
                registration.register(NsUserTest.SANJIGI, 90L)
        ).isInstanceOf(IllegalStateException.class);
    }
}
