package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public interface EnrollmentPolicy {
  MoneyType moneyType();
  void checkEnrollAvailability(Session session, Payment payment);
}