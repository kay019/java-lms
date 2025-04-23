package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.ArrayList;

public class FreeSession extends Session {
    public FreeSession(Long id, Period period, CoverImage coverImage, SessionStatus status, Students students) {
        super(id, period, coverImage, status, SessionType.FREE, students);
    }

    public FreeSession(Long id, Period period, CoverImage coverImage, SessionStatus status) {
        this(id, period, coverImage, status, new Students(new ArrayList<>()));
    }

    public FreeSession(Period period, CoverImage coverImage, SessionStatus status) {
        this(0L, period, coverImage, status, new Students(new ArrayList<>()));
    }

    @Override
    protected Payment createPayment(Student student) {
        return new Payment(id, student.getId(), 0L);
    }
}
