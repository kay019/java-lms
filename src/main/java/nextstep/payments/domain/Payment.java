package nextstep.payments.domain;

import nextstep.common.domian.BaseDomain;
import nextstep.session.domain.Session;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment extends BaseDomain {

    private Session session;

    private NsUser user;

    private Long amount;

    public Payment() {
    }

    public Payment(Long id, Session session, NsUser user, Long amount) {
        super(id, LocalDateTime.now(), LocalDateTime.now());
        this.session = session;
        this.user = user;
        this.amount = amount;
    }

    public boolean equalsSessionUser(Payment payment) {
        return payment.user.equals(this.user) && payment.session.equals(this.session);
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
