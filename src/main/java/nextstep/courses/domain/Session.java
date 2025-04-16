package nextstep.courses.domain;

import nextstep.courses.domain.strategy.EnrollmentStrategy;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;

public class Session {
    private Long id;
    private int enrolledCount = 0;
    private SessionFee fee;
    private NsUsers enrollees = new NsUsers();
    private SessionStatus status = SessionStatus.PREPARING;
    private EnrollmentStrategy enrollmentStrategy;
    private CoverImage coverImage;
    private SessionDate sessionDate;

    public Session(int fee,
                   SessionStatus status,
                   EnrollmentStrategy enrollmentStrategy,
                   CoverImage coverImage,
                   SessionDate sessionDate) {
        this.fee = new SessionFee(fee);
        this.status = status;
        this.enrollmentStrategy = enrollmentStrategy;
        this.coverImage = coverImage;
        this.sessionDate = sessionDate;
    }


    public Payment enroll(NsUser nsUser, Payment payment) {
        if (!this.status.isAvailableEnrollment()) {
            throw new IllegalArgumentException("강의 상태가 PREPARING이 아닙니다.");
        }

        enrollmentStrategy.validateEnrollment(this, payment);
        enrollees.add(nsUser);

        return new Payment("", this, nsUser, payment.getAmount());
    }

    public boolean isExceedLimitEnrollmentCount(int enrollmentLimitCount) {
        return this.enrolledCount >= enrollmentLimitCount;
    }

    public boolean isSameFee(Payment payment) {
        return this.fee.isEqual(payment.getAmount());
    }

}
