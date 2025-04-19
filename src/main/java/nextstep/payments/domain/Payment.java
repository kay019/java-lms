package nextstep.payments.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.courses.domain.session.Session;
import nextstep.payments.entity.PaymentEntity;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment extends BaseDomain {

    private Session session;

    private NsUser user;

    private Long amount;

    public static Payment from(PaymentEntity paymentEntity, Session session, NsUser nsUser) {
        return new Payment(
            paymentEntity.getId(),
            paymentEntity.isDeleted(),
            paymentEntity.getCreatedAt(),
            paymentEntity.getUpdatedAt(),
            session,
            nsUser,
            paymentEntity.getAmount()
        );
    }

    public Payment() {
    }

    public Payment(Session session, NsUser user, Long amount) {
        this(null, session, user, amount);
    }

    public Payment(String id, Session session, NsUser user, Long amount) {
        super(id, LocalDateTime.now(), LocalDateTime.now());
        this.session = session;
        this.user = user;
        this.amount = amount;
    }

    public Payment(String id, boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt, Session session, NsUser user, Long amount) {
        super(id, deleted, createdAt, updatedAt);
        this.session = session;
        this.user = user;
        this.amount = amount;
    }

    public boolean equalsSessionUser(Payment payment) {
        return payment.user.equals(this.user) && payment.session.equals(this.session);
    }

    public boolean isSameSession(Payment payment) {
        return payment.session.equals(this.session);
    }

    public boolean canEnroll(Session session, int enrollCount) {
        return session.canEnroll(enrollCount, amount);
    }

    public PaymentEntity toPaymentEntity() {
        return PaymentEntity.builder()
            .id(id())
            .userId(user.id())
            .sessionId(session.id())
            .amount(amount)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .deleted(deleted)
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(session, payment.session) &&
            Objects.equals(user, payment.user) &&
            Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), session, user, amount);
    }
}
