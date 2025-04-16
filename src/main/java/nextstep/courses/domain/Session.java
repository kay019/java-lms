package nextstep.courses.domain;

import java.time.LocalDate;
import java.util.List;

import nextstep.payments.domain.Payment;

public abstract class Session {
    private long id;
    private SessionCoverImage coverImage;
    private SessionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<Student> students;

    public abstract void enroll(Student student, Payment payment);
}
