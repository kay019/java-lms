package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

import nextstep.payments.domain.Payment;

public abstract class Session {

    private final Long id;

    private final Image coverImage;

    private final Period period;

    private final List<Participant> participants = new ArrayList<>();

    private SessionStatus status = SessionStatus.PREPARING;

    private Session(Long id, Image coverImage, Period period) {
        validate(coverImage, period);

        this.id = id;
        this.coverImage = coverImage;
        this.period = period;
    }
    
    protected Session(Image coverImage, Period period) {
        this(null, coverImage, period);
    }

    private void validate(Image coverImage, Period period) {
        if (coverImage == null) {
            throw new IllegalArgumentException("커버 이미지는 필수 값입니다.");
        }

        if (period == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수 값입니다.");
        }
    }

    public boolean isPreparing() {
        return status.equals(SessionStatus.PREPARING);
    }

    public boolean isEnrolling() {
        return status.equals(SessionStatus.ENROLLING);
    }

    public boolean isClosed() {
        return status.equals(SessionStatus.CLOSED);
    }

    public void openEnrollment() {
        status = status.openEnrollment();
    }

    public void closeEnrollment() {
        status = status.closeEnrollment();
    }

    public Participant enroll(Long userId, Payment payment) {
        status.assertCanEnroll();
        validateEnrollment(payment);

        Participant participant = new Participant(userId);
        participants.add(participant);
        return participant;
    }

    protected abstract void validateEnrollment(Payment payment);

    protected int getParticipantsCount() {
        return participants.size();
    }

    public boolean isParticipant(Long userId) {
        return participants.contains(new Participant(userId));
    }

}
