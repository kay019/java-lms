package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;

public enum SessionType {
    FREE{
        @Override
        public void validate(Enrollments enrollments, Payment payment, int maxParticipants, long fee) {
        }
    },
    PAID {
        @Override
        public void validate(Enrollments enrollments, Payment payment, int maxParticipants, long fee) {
            if(maxParticipants <= 0) {
                throw new IllegalArgumentException("수강 최대 인원은 0 이상이어야 합니다.");
            }
            enrollments.checkPossibleEnroll(maxParticipants);
            payment.checkMatchFee(fee);
        }
    };


    public abstract void validate(Enrollments enrollments, Payment payment, int maxParticipants, long fee);
}
