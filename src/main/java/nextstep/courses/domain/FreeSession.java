package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    public FreeSession(Long id, SessionStatus status, SessionDate date,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, status, date, createdAt, updatedAt);
    }

    public FreeSession(Long id, SessionStatus status, SessionDate date) {
        super(id, status, date);
    }

    @Override
    public void enroll(Payment payment) {
        status.validateEnroll();
        enrollStudent(payment.getNsUser());
    }

    private void enrollStudent(NsUser user) {
        Student student = new Student(user);
        students.add(student);
    }

}
