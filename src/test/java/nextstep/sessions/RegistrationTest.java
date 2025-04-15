package nextstep.sessions;

import nextstep.sessions.domain.Registration;
import nextstep.sessions.domain.RegistrationType;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegistrationTest {
    @Test
    void testFreeRegistration() {
        Registration registration = Registration.createFreeRegistration(RegistrationType.FREE);
        registration.register(NsUserTest.JAVAJIGI, 0L);
        assertThat(registration.contains(NsUserTest.JAVAJIGI)).isTrue();
    }

    @Test
    void testPaidRegistration() {
        Registration registration = Registration.createPaidRegistration(RegistrationType.PAID, 100L, 10);
        registration.register(NsUserTest.JAVAJIGI, 100L);
        assertThat(registration.contains(NsUserTest.JAVAJIGI)).isTrue();
    }

    @Test
    void testFreeRegistrationException() {
        assertThatThrownBy(() -> Registration.createFreeRegistration(RegistrationType.PAID))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testPaidRegistrationException() {
        assertThatThrownBy(() -> Registration.createPaidRegistration(RegistrationType.FREE, 100L, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testOverMaxStudentException() {
        Registration registration = Registration.createPaidRegistration(RegistrationType.PAID, 100L, 1);
        registration.register(NsUserTest.JAVAJIGI, 100L);
        assertThatThrownBy(() ->
            registration.register(NsUserTest.SANJIGI, 100L)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testNotEqualFeeException() {
        Registration registration = Registration.createPaidRegistration(RegistrationType.PAID, 100L, 1);
        assertThatThrownBy(() ->
                registration.register(NsUserTest.SANJIGI, 90L)
        ).isInstanceOf(IllegalStateException.class);
    }
}
