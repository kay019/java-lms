package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Enrollment {
    private final Session session;
    private final NsUser nsUser;
    private final Payment payment;
    private final LocalDateTime createdAt;

    public Enrollment(Session session, NsUser nsUser) {
        this(session, nsUser, null);
    }

    public Enrollment(Session session, NsUser nsUser, Payment payment) {
        this.session = session;
        this.nsUser = nsUser;
        this.payment = payment;
        this.createdAt = LocalDateTime.now();
    }
}
