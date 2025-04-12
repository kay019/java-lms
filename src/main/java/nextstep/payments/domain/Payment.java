package nextstep.payments.domain;

import nextstep.session.domain.Session;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private Long id;

    private Session session;

    private NsUser user;

    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(Long id, Session session, NsUser user, Long amount) {
        this.id = id;
        this.session = session;
        this.user = user;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isSameAmount(Long amount) {
        return Objects.equals(this.amount, amount);
    }

    public boolean equalsSessionUser(Payment payment) {
        return payment.user.equals(this.user) && payment.session.equals(this.session);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
            Objects.equals(session, payment.session) &&
            Objects.equals(user, payment.user) &&
            Objects.equals(amount, payment.amount) &&
            Objects.equals(createdAt, payment.createdAt)
            ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, session, user, amount, createdAt);
    }
}
