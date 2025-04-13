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
        registration.register(NsUserTest.JAVAJIGI);
        registration.register(NsUserTest.SANJIGI);
    }

    @Test
    void testPaidRegistrationException() {
        Registration registration = new Registration(RegistrationType.PAID, 1);
        registration.register(NsUserTest.JAVAJIGI);
        assertThatThrownBy(() ->
            registration.register(NsUserTest.SANJIGI)
        ).isInstanceOf(IllegalStateException.class);
    }
}
