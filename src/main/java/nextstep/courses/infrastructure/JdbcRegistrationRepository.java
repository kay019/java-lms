package nextstep.courses.infrastructure;

import nextstep.courses.domain.Registration;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRegistrationRepository implements RegistrationRepository {

    @Override
    public Registration save(final Registration registration) {
        return null;
    }
}
