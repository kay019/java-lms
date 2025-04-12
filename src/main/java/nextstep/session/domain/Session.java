package nextstep.session.domain;

import nextstep.Identifiable;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session implements Identifiable {
    private Long id;
    private Long courseId;
    private final CoverImage coverImage;
    private final SessionStatus sessionStatus;
    private final RegistrationPolicy registrationPolicy;
    private final SessionPeriod sessionPeriod;

    public Session(Long id, CoverImage coverImage, SessionStatus sessionStatus, RegistrationPolicy registrationPolicy, SessionPeriod sessionPeriod) {
        this(id, null, coverImage, sessionStatus, registrationPolicy, sessionPeriod);
    }

    public Session(Long id, Long courseId, CoverImage coverImage, SessionStatus sessionStatus, RegistrationPolicy registrationPolicy, SessionPeriod sessionPeriod) {
        this.id = id;
        this.courseId = courseId;
        this.coverImage = coverImage;
        this.sessionStatus = sessionStatus;
        this.registrationPolicy = registrationPolicy;
        this.sessionPeriod = sessionPeriod;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public RegistrationPolicyType getRegistrationPolicyType() {
        return RegistrationPolicyType.fromClass(registrationPolicy.getClass());
    }

    public long getSessionFee() {
        return registrationPolicy.getSessionFee();
    }

    public int getMaxStudentCount() {
        return registrationPolicy.getMaxStudentCount();
    }

    public LocalDateTime getStartedAt() {
        return sessionPeriod.getStartedAt();
    }

    public LocalDateTime getEndedAt() {
        return sessionPeriod.getEndedAt();
    }

    public Payment register(NsUser nsUser, Money paymentAmount, int currentStudentCount) {
        if (!sessionStatus.isRegistrable()) {
            throw new IllegalStateException("수강신청이 불가능한 상태입니다.");
        }

        registrationPolicy.validateRegistration(currentStudentCount, paymentAmount);

        return new Payment(this.getId(), nsUser.getId(), paymentAmount);
    }

    @Override
    public boolean isUnsaved() {
        return getId() == null;
    }
}
