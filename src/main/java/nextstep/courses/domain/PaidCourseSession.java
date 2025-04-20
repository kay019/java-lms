package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class PaidCourseSession extends CourseSession {
    private int maxParticipantSize;
    private long sessionPrice;

    public PaidCourseSession(Long courseId, SessionCoverImage image, SessionStatus status, String title, int maxParticipantSize, long sessionPrice, SessionPeriod period) {
        super(courseId, image, SessionType.PAID, status, title, period);
        this.maxParticipantSize = maxParticipantSize;
        this.sessionPrice = sessionPrice;
    }

    @Override
    void checkPossibleToRegister(Long nsUserId, Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }

        if (!getId().equals(payment.getSessionId())) {
            throw new IllegalStateException("Session id mismatch");
        }

        if (!nsUserId.equals(payment.getNsUserId())) {
            throw new IllegalStateException("NsUserId mismatch");
        }

        if (registeredUserSize() >= maxParticipantSize) {
            throw new IllegalStateException("This class has reached its maximum capacity.");
        }

        if (payment.getAmount() != sessionPrice) {
            throw new IllegalStateException("Payment amount is not equal to price amount.");
        }
    }
}
