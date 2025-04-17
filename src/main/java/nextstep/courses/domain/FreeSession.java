package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(Long id, String name, Period period, Image coverImage, SessionStatus status) {
        super(id, name, period, coverImage, status);
    }

    @Override
    protected void validateRegistration(Long studentId, Payment payment) {
    }

    @Override
    public SessionType getType() {
        return SessionType.FREE;
    }
}