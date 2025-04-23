package nextstep.courses.enrollment.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Enrollment {
    private final Long id;
    private final Long sessionId;
    private NsUser nsUser;
    private Payment payment;
    private EnrollmentStatus status;
    private final LocalDateTime createdAt;

    public Enrollment(Long sessionId, NsUser nsUser, Payment payment) {
        this(null, sessionId, nsUser, payment, EnrollmentStatus.APPROVED, LocalDateTime.now());
    }

    public static Enrollment enrollToSelectedCourse(Long sessionId, NsUser user, Payment payment) {
        return new Enrollment(null, sessionId, user, payment, EnrollmentStatus.APPLIED, LocalDateTime.now());
    }

    public static Enrollment enrollToGeneralCourse(Long sessionId, NsUser user, Payment payment) {
        return new Enrollment(null, sessionId, user, payment, EnrollmentStatus.APPROVED, LocalDateTime.now());
    }
    public Enrollment(Long id, Long sessionId, NsUser nsUser, Payment payment, EnrollmentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUser = nsUser;
        this.payment = payment;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void approve() {
        if (status != EnrollmentStatus.APPLIED) {
            throw new IllegalArgumentException("신청 상태에서만 승인할 수 있습니다.");
        }
        this.status = EnrollmentStatus.APPROVED;
    }

    public void cancel() {
        this.status = EnrollmentStatus.CANCELED;
    }

    public Enrollment injectUserAndPayment(NsUser user, Payment payment) {
        this.nsUser = user;
        this.payment = payment;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public Payment getPayment() {
        return payment;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
