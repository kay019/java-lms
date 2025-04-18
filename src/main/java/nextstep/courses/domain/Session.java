package nextstep.courses.domain;

import nextstep.courses.domain.image.CoverImage;
import nextstep.payments.domain.Payment;

public abstract class Session {
    protected Long id;

    protected CoverImage coverImage;

    protected SessionStatus status;

    protected SessionDate date;

    protected Students students;

    public Session(Long id, CoverImage coverImage,
                   SessionStatus status, SessionDate date) {
        this.id = id;
        this.coverImage = coverImage;
        this.status = status;
        this.date = date;
        this.students = new Students();
    }

    public abstract void enroll(Payment payment);

    public int getStudentSize() {
        return students.size();
    }
}
