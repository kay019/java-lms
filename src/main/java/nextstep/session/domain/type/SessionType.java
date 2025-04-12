package nextstep.session.domain.type;

import nextstep.session.domain.Session;

public enum SessionType {
    FREE((session) -> true),
    PAID(Session::isCapacityNotExceeded);

    private final RegistrationValidator registrationValidator;

    SessionType(RegistrationValidator registrationValidator) {
        this.registrationValidator = registrationValidator;
    }

    public boolean isRegistrationEnable(Session session) {
        return this.registrationValidator.isEnable(session);
    }
}
