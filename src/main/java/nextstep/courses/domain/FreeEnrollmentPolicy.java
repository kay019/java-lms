package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeEnrollmentPolicy implements EnrollmentPolicy {

  @Override
  public void checkEnrollAvailability(Session session) {
  }

  @Override
  public void checkPayment(Payment payment) {
  }

  @Override
  public boolean isFull(Enrollments enrollments) {
    return false;
  }
}
