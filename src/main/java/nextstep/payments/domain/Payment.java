package nextstep.payments.domain;

import java.time.LocalDateTime;

import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public class Payment {
    private String id;

    private Session session;

    // 결제한 사용자
    private NsUser nsUser;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, NsUser nsUser, Long amount) {
        this.id = id;
        this.nsUser = nsUser;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Payment(String id, Session session, NsUser nsUser, Long amount) {
        this(id, nsUser, amount);
        this.session = session;
    }

    public Long getAmount() {
        return amount;
    }
}
