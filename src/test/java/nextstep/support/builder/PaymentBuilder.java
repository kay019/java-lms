package nextstep.support.builder;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

public class PaymentBuilder {
    private String id = "1";
    private Session session;
    private NsUser nsUser = NsUserTest.JAVAJIGI;
    private Long amount = 10_000L;

    public PaymentBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PaymentBuilder session(Session session) {
        this.session = session;
        return this;
    }

    public PaymentBuilder nsUser(NsUser nsUser) {
        this.nsUser = nsUser;
        return this;
    }

    public PaymentBuilder amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Payment build() {
        return new Payment(id, session, nsUser, amount);
    }
}
