package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public interface EnrollmentPolicy {
  MoneyType moneyType();
  void checkEnrollAvailability(Session session, NsUser user, Payment payment);
}