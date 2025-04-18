package nextstep.courses.domain;

import nextstep.exception.EnrollmentIllegalArgumentException;
import nextstep.payments.domain.Payment;

public class Enrollment {
    private final Student student;
    private final Money money;

    public Enrollment(Student student, Payment payment) {
        validate(student, payment);

        this.student = student;
        this.money = new Money(payment.getAmount());
    }

    private void validate(Student student, Payment payment) {
        if (student == null || payment == null) {
            throw new EnrollmentIllegalArgumentException();
        }
    }

    public Student getStudent() {
        return student;
    }

    public boolean isNotValid(int amount) {
        return !money.isValid(amount);
    }
}
