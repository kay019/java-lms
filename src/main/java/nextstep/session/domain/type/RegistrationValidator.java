package nextstep.session.domain.type;

import nextstep.session.domain.Session;

public interface RegistrationValidator {
    boolean isEnable(Session session);
}
