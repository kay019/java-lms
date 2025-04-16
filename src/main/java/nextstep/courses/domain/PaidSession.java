package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class PaidSession extends Session {
    private int maxCapacity;
    private int fee;

    @Override
    public void enroll(Student student, Payment payment) {

    }
}
