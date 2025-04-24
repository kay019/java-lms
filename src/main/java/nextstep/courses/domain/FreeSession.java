package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    public FreeSession(Long id, SessionStatus sessionStatus, EnrollStatus enrollStatus,
                       SessionDate date, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, sessionStatus, enrollStatus, date, createdAt, updatedAt);
    }

    public FreeSession(Long id, SessionStatus sessionStatus,
                       EnrollStatus enrollStatus, SessionDate date) {
        super(id, sessionStatus, enrollStatus, date);
    }

    @Override
    public void enroll(Payment payment) {
        enrollStatus.validateEnroll();
        enrollStudent(payment.getNsUser());
    }

    private void enrollStudent(NsUser user) {
        Student student = new Student(user);
        students.add(student);
    }

}
