package nextstep.session.domain;

import nextstep.Identifiable;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.sessionstudent.SessionStudent;
import nextstep.sessionstudent.SessionStudentStatus;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.List;

public class Session implements Identifiable {
    private Long id;
    private Long courseId;
    private final CoverImages coverImages;
    private final SessionProgressStatus progressStatus;
    private final RecruitmentStatus recruitmentStatus;
    private final RegistrationPolicy registrationPolicy;
    private final SessionPeriod sessionPeriod;
    private final boolean selectionRequired;

    public Session(Long id, Long courseId, CoverImages coverImages, SessionProgressStatus progressStatus, RecruitmentStatus recruitmentStatus, RegistrationPolicy registrationPolicy, SessionPeriod sessionPeriod, boolean selectionRequired) {
        this.id = id;
        this.courseId = courseId;
        this.coverImages = coverImages;
        this.progressStatus = progressStatus;
        this.recruitmentStatus = recruitmentStatus;
        this.registrationPolicy = registrationPolicy;
        this.sessionPeriod = sessionPeriod;
        this.selectionRequired = selectionRequired;
        initRelation();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        initRelation();
    }

    public Long getCourseId() {
        return courseId;
    }

    public CoverImages getCoverImages() {
        return coverImages;
    }

    public SessionProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public RecruitmentStatus getRecruitmentStatus() {
        return recruitmentStatus;
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

    public boolean isSelectionRequired() {
        return selectionRequired;
    }

    public LocalDateTime getStartedAt() {
        return sessionPeriod.getStartedAt();
    }

    public LocalDateTime getEndedAt() {
        return sessionPeriod.getEndedAt();
    }

    public void addCoverImage(CoverImage coverImage) {
        coverImage.setSessionId(getId());
        coverImages.add(coverImage);
    }

    public SessionStudent register(NsUser nsUser, Money paymentAmount, int currentStudentCount) {
        if (!recruitmentStatus.isRecruiting()) {
            throw new IllegalStateException("수강신청이 불가능한 상태입니다.");
        }

        registrationPolicy.validateRegistration(currentStudentCount, paymentAmount);

        return new SessionStudent(this.getId(), nsUser.getId(), isSelectionRequired());
    }

    private void initRelation() {
        coverImages.setSessionId(id);
    }

    @Override
    public boolean isUnsaved() {
        return getId() == null;
    }
}
