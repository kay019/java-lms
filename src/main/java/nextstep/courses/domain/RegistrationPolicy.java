package nextstep.courses.domain;

public interface RegistrationPolicy {
    void validateRegistration(Session session, Money paymentAmount);
    long getSessionFee();
    int getMaxStudentCount();
}
