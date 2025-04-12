package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

public interface SessionType {

    boolean enroll(Payment payment, Session session);
}
