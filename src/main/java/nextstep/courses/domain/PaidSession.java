package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class PaidSession extends Session {
    private final int fee;
    private final int maxStudent;

    public PaidSession(Long id, SessionStatus sessionStatus, EnrollStatus enrollStatus,
                       SessionDate date, LocalDateTime createdAt, LocalDateTime updatedAt,
                       int fee, int maxStudent) {
        super(id, sessionStatus, enrollStatus, date, createdAt, updatedAt);
        this.fee = fee;
        this.maxStudent = maxStudent;
    }

    public PaidSession(Long id, SessionStatus sessionStatus, EnrollStatus enrollStatus,
                       SessionDate date,
                       int fee, int maxStudent) {
        super(id, sessionStatus, enrollStatus, date);
        this.fee = fee;
        this.maxStudent = maxStudent;
    }

    @Override
    public Enrollment requestEnroll(int approvedStudent, Payment payment) {
        enrollStatus.validateEnroll();
        validateEnroll(approvedStudent, payment.getAmount());
        return Enrollment.requestEnroll(payment.getSessionId(), payment.getNsUserId());
    }

    private void validateEnroll(int approvedStudent, Long paymentAmount) {
        if (approvedStudent >= maxStudent) {
            throw new IllegalArgumentException("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
        }
        if (fee != paymentAmount) {
            throw new IllegalArgumentException("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
        }
    }

    public int getFee() {
        return fee;
    }

    public int getMaxStudent() {
        return maxStudent;
    }
}
