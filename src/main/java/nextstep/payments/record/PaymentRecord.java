package nextstep.payments.record;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import nextstep.courses.domain.Money;
import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;

public class PaymentRecord {
    private Long id;
    private Long sessionId;
    private Long nsUserId;
    private NsUser nsUser;
    private long amount;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public void setNsUserId(Long nsUserId) {
        this.nsUserId = nsUserId;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setNsUser(NsUser nsUser) {
        this.nsUser = nsUser;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public static PaymentRecord from(Payment payment) {
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setId(payment.getId());
        paymentRecord.setSessionId(payment.getSession().getId());
        paymentRecord.setNsUserId(payment.getNsUser().getId());
        paymentRecord.setAmount(payment.getAmount().getAmount());
        paymentRecord.setCreatedAt(payment.getCreatedAt());
        return paymentRecord;
    }

    public static PaymentRecords from(Payments payments) {
        List<PaymentRecord> records = payments.getPayments()
            .stream()
            .map(PaymentRecord::from)
            .collect(Collectors.toList());

        return new PaymentRecords(records);
    }

    public Payment toPayment(Session session) {
        return new Payment(getId(), session, getNsUser(), new Money(getAmount()), getCreatedAt());
    }
}
