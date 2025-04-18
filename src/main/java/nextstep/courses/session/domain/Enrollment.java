package nextstep.courses.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Enrollment {
    private final NsUser nsUser;
    private final Payment payment;
    private final LocalDateTime createdAt;

    public Enrollment(NsUser nsUser) {
        this(nsUser, null);
    }

    public Enrollment(NsUser nsUser, Payment payment) {
        this.nsUser = nsUser;
        this.payment = payment;
        this.createdAt = LocalDateTime.now();
    }
}
