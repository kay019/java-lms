package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class PaidSession extends Session {
    private Capacity maxCapacity;
    private TuitionFee tuitionFee;

    public PaidSession(Long id, String name, Period period, Image coverImage,
                       SessionStatus status, int maxCapacity, int tuitionFee) {
        super(id, name, period, coverImage, status);
        this.maxCapacity = new Capacity(maxCapacity);
        this.tuitionFee = new TuitionFee(tuitionFee);
    }

    @Override
    protected void validateRegistration(Long studentId, Payment payment) {
        if (registeredStudents.size() >= maxCapacity.getValue()) {
            throw new IllegalStateException("수강 인원을 초과하였습니다.");
        }
        if (payment == null) {
            throw new IllegalArgumentException("결제 정보가 없습니다.");
        }
        payment.isValidatePayment(id, studentId, tuitionFee);
    }

    @Override
    public SessionType getType() {
        return SessionType.PAID;
    }
}