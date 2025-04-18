package nextstep.session.domain;

public interface RegistrationPolicy {
    void validateRegistration(int currentStudentCount, Money paymentAmount);
    long getSessionFee();
    int getMaxStudentCount();
}
