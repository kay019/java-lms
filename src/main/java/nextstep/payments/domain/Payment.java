package nextstep.payments.domain;

import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private Session session;
    private NsUser nsUser;
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

    public boolean isSameAmount(Long targetAmount) {
        return this.amount.equals(targetAmount);
    }
}
