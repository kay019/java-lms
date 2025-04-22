package nextstep.courses.enrollment.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Enrollment {
    private final Long id;
    private final Long sessionId;
    private final NsUser nsUser;
    private final Payment payment;
    private EnrollmentStatus status;
    private final boolean selected;
    private final LocalDateTime createdAt;

    public Enrollment(Long sessionId, NsUser nsUser, Payment payment) {
        this(null, sessionId, nsUser, payment, EnrollmentStatus.APPROVED, false, LocalDateTime.now());
    }

    public static Enrollment enrollToSelectedCourse(Long sessionId, NsUser user, Payment payment) {
        return new Enrollment(null, sessionId, user, payment, EnrollmentStatus.APPLIED, false, LocalDateTime.now());
    }

    public static Enrollment enrollToGeneralCourse(Long sessionId, NsUser user, Payment payment) {
        return new Enrollment(null, sessionId, user, payment, EnrollmentStatus.APPROVED, false, LocalDateTime.now());
    }
    public Enrollment(Long id, Long sessionId, NsUser nsUser, Payment payment, EnrollmentStatus status, boolean selected, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUser = nsUser;
        this.payment = payment;
        this.status = status;
        this.selected = selected;
        this.createdAt = createdAt;
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

    public boolean isSelected() {
        return selected;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
