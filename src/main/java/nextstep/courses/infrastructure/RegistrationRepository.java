package nextstep.courses.infrastructure;

import nextstep.courses.domain.Registration;

import java.util.Optional;

public interface RegistrationRepository {

    Registration save(final Registration registration);
} 
