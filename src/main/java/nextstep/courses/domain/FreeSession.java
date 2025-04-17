package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDate;

public class FreeSession extends Session {

    public FreeSession(Long id, String name, LocalDate startDate, LocalDate endDate, Image coverImage, LectureStatus status) {
        super(id, name, startDate, endDate, coverImage, status);
    }

    @Override
    protected void validateRegistration(Long studentId, Payment payment) {
    }

    @Override
    public SessionType getType() {
        return SessionType.FREE;
    }
}