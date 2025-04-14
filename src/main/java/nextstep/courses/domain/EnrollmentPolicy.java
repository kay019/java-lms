package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public interface EnrollmentPolicy {
  void checkEnrollAvailability(Session session);
  void checkPayment(Payment payment);
}