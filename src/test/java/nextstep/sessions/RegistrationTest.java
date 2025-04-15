package nextstep.sessions;

import nextstep.sessions.domain.Registration;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationTest {
    @Test
    void testCreateRegistration() {
        assertThat(new Registration(Set.of(NsUserTest.JAVAJIGI, NsUserTest.SANJIGI))).isNotNull();
    }

    @Test
    void testRegistrationSize() {
        Registration registration = new Registration();
        registration.register(NsUserTest.JAVAJIGI);
        registration.register(NsUserTest.JAVAJIGI);
        registration.register(NsUserTest.SANJIGI);
        assertThat(registration.registeredUserCount()).isEqualTo(2);
    }
}
