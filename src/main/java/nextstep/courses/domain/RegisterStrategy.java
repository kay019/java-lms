package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public interface RegisterStrategy {
    public Payment register(NsUser user, Long sessionId, int studentCount, NaturalNumber money);
}
