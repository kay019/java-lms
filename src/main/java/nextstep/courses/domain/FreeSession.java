package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.ArrayList;

public class FreeSession extends Session {
    public FreeSession(Long id, Period period, CoverImages coverImages, SessionStatus status, Students students) {
        super(id, period, coverImages, status, SessionType.FREE, students);
    }

    public FreeSession(Long id, Period period, CoverImages coverImages, SessionStatus status) {
        this(id, period, coverImages, status, new Students(new ArrayList<>()));
    }

    public FreeSession(Period period, CoverImages coverImages, SessionStatus status) {
        this(0L, period, coverImages, status, new Students(new ArrayList<>()));
    }

    @Override
    protected Payment createPayment(Student student) {
        return new Payment(id, student.getId(), 0L);
    }

    @Override
    public FreeSession withStudents(Students students) {
        return new FreeSession(id, period, coverImages, status, students);
    }

    @Override
    public FreeSession withCoverImages(CoverImages coverImages) {
        return new FreeSession(id, period, coverImages, status, students);
    }
}
