package nextstep.payments.domain;

import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private Session session;

    // 결제한 사용자 아이디
    private NsUser nsUser;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Session session, NsUser nsUser, Long amount) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Long getAmount() {
        return amount;
    }

    public NsUser getNsUser() {
        return nsUser;
    }
}
