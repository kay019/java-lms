package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeEnrollmentPolicy implements EnrollmentPolicy{

    @Override
    public MoneyType moneyType() {
        return MoneyType.FREE;
    }

    @Override
    public void checkEnrollAvailability(Session session, Payment payment) {}
}
