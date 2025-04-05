package nextstep.courses.domain;

public class PaidRegistrationPolicy implements RegistrationPolicy {

    private final Money sessionFee;
    private final NaturalNumber maxStudentCount;

    public PaidRegistrationPolicy(int sessionFee, int maxStudentCount) {
        this.sessionFee = new Money(sessionFee);
        this.maxStudentCount = new NaturalNumber(maxStudentCount);
    }

    @Override
    public void validateRegistration(Session session, Money paymentAmount) {
        if (maxStudentCount
            .compareTo(session.getStudentCount()) <= 0) {
            throw new IllegalArgumentException("강의 최대 수강 인원을 초과할 수 없습니다.");
        }

        if (!sessionFee.equals(paymentAmount)) {
            throw new IllegalArgumentException("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
        }
    }

}
