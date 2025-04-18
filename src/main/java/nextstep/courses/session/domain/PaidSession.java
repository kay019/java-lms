package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;

public class PaidSession extends Session {
    private final int maxParticipants;
    private final Long fee;

    public PaidSession(SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus, int maxParticipants, Long fee) {
        this(sessionDate, sessionCoverImage, sessionStatus, new Enrollments(new ArrayList<>()), maxParticipants, fee);
    }

    public PaidSession(SessionDate sessionDate, SessionCoverImage sessionCoverImage, SessionStatus sessionStatus, Enrollments enrollments, int maxParticipants, Long fee) {
        super(sessionDate, sessionCoverImage, sessionStatus, enrollments);
        validate(maxParticipants);
        this.maxParticipants = maxParticipants;
        this.fee = fee;
    }

    @Override
    protected void preEnrollValidation(Payment payment, NsUser nsUser) {
        enrollments.checkPossibleEnroll(maxParticipants);
        payment.checkMatchFee(fee);
    }

    private void validate(int maxParticipants) {
        if (maxParticipants <= 0) {
            throw new IllegalArgumentException("수강 최대 인원은 0 이상이어야 합니다.");
        }
    }
}
