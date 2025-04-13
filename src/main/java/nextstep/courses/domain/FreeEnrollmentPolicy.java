package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class FreeEnrollmentPolicy implements EnrollmentPolicy{
    @Override
    public void checkEnrollAvailability(Session session, NsUser user, Payment payment) {}
}
