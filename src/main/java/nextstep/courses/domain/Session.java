package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.payments.record.PaymentRecord;
import nextstep.payments.record.PaymentRecords;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.List;

public class Session {
    private Long id;
    private Course course;
    private final Payments payments;
    private CoverImage coverImage;
    private SessionStatus sessionStatus;
    private RegistrationPolicy registrationPolicy;
    private SessionPeriod sessionPeriod;

    public Session(Long id, CoverImage coverImage, SessionStatus sessionStatus, RegistrationPolicy registrationPolicy, SessionPeriod sessionPeriod) {
        this(id, null, coverImage, sessionStatus, registrationPolicy, sessionPeriod, new PaymentRecords());
    }

    public Session(Long id, Course course, CoverImage coverImage, SessionStatus sessionStatus, RegistrationPolicy registrationPolicy, SessionPeriod sessionPeriod, PaymentRecords paymentRecords) {
        this.id = id;
        this.course = course;
        this.coverImage = coverImage;
        this.sessionStatus = sessionStatus;
        this.registrationPolicy = registrationPolicy;
        this.sessionPeriod = sessionPeriod;
        this.payments = paymentRecords.toPayments(this);
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Payments getPayments() {
        return payments;
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

    public void toCourse(Course course) {
        this.course = course;
    }

    public boolean isStudentCountLessThan(int count) {
        return payments.getSize() < count;
    }

    public Payment register(NsUser nsUser, Money paymentAmount) {
        if (!sessionStatus.isRegistrable()) {
            throw new IllegalStateException("수강신청이 불가능한 상태입니다.");
        }

        registrationPolicy.validateRegistration(this, paymentAmount);

        Payment payment = new Payment(this, nsUser, paymentAmount);
        payments.add(payment);

        return payment;
    }


}
