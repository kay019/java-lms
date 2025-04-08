package nextstep.courses.domain;

public class FreeRegistrationPolicy implements RegistrationPolicy {

    @Override
    public void validateRegistration(Session session, Money paymentAmount) {
        // 무조건 패스
    }

    @Override
    public long getSessionFee() {
        return 0;
    }

    @Override
    public int getMaxStudentCount() {
        return 0;
    }
}
