package nextstep.courses.record;

import nextstep.courses.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.payments.record.PaymentRecord;
import nextstep.payments.record.PaymentRecords;

import java.time.LocalDateTime;
import java.util.List;

public class SessionRecord {
    private Long id;
    private long courseId;
    private long coverImageId;
    private SessionStatus sessionStatus;
    private RegistrationPolicyType registrationPolicyType;
    private long sessionFee;
    private int maxStudentCount;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public Long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getCoverImageId() {
        return coverImageId;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public RegistrationPolicyType getRegistrationPolicyType() {
        return registrationPolicyType;
    }

    public long getSessionFee() {
        return sessionFee;
    }

    public int getMaxStudentCount() {
        return maxStudentCount;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setCoverImageId(long coverImageId) {
        this.coverImageId = coverImageId;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public void setRegistrationPolicyType(RegistrationPolicyType registrationPolicyType) {
        this.registrationPolicyType = registrationPolicyType;
    }

    public void setSessionFee(long sessionFee) {
        this.sessionFee = sessionFee;
    }

    public void setMaxStudentCount(int maxStudentCount) {
        this.maxStudentCount = maxStudentCount;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session toSession(Course course, CoverImage coverImage, PaymentRecords payments) {
        SessionPeriod sessionPeriod = createSessionPeriod();
        RegistrationPolicy registrationPolicy = createRegistrationPolicy();
        return new Session(getId(), course, coverImage, getSessionStatus(), registrationPolicy, sessionPeriod, payments);
    }

    private SessionPeriod createSessionPeriod() {
        return new SessionPeriod(getStartedAt(), getEndedAt());
    }

    private RegistrationPolicy createRegistrationPolicy() {
        return getRegistrationPolicyType().createPolicy(getSessionFee(), getMaxStudentCount());
    }

    public static SessionRecord from(Session session) {
        SessionRecord sessionRecord = new SessionRecord();
        sessionRecord.setId(session.getId());
        sessionRecord.setCourseId(session.getCourse().getId());
        sessionRecord.setCoverImageId(session.getCoverImage().getId());
        sessionRecord.setSessionStatus(session.getSessionStatus());
        sessionRecord.setRegistrationPolicyType(session.getRegistrationPolicyType());
        sessionRecord.setSessionFee(session.getSessionFee());
        sessionRecord.setMaxStudentCount(session.getMaxStudentCount());
        sessionRecord.setStartedAt(session.getStartedAt());
        sessionRecord.setEndedAt(session.getEndedAt());
        return sessionRecord;
    }
}
