package nextstep.payments.domain;

import java.time.LocalDateTime;

import nextstep.courses.domain.Money;
import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public class Payment {
    private String id;

    // 결제한 강의
    private Session session;

    // 결제한 사용자
    private NsUser nsUser;

    // 결제 금액
    private Money amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Session session, NsUser nsUser, Money amount) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Session getSession() {
        return session;
    }

    public NsUser getNsUser() {
        return nsUser;
    }

    public Money getAmount() {
        return amount;
    }
}
