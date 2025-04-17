package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDate;

public class PaidSession extends Session {
    private int maxCapacity;
    private Long tuitionFee;

    public PaidSession(Long id, String name, LocalDate startDate, LocalDate endDate, Image coverImage,
                       LectureStatus status, int maxCapacity, Long tuitionFee) {
        super(id, name, startDate, endDate, coverImage, status);
        this.maxCapacity = maxCapacity;
        this.tuitionFee = tuitionFee;
    }

    @Override
    protected void validateRegistration(Long studentId, Payment payment) {
        if (registeredStudents.size() >= maxCapacity) {
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