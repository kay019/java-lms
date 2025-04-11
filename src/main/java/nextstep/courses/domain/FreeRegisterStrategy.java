package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class FreeRegisterStrategy implements RegisterStrategy {
    @Override
    public Payment register(NsUser user, Long sessionId, NaturalNumber money) {
        return new Payment("", sessionId, user.getId(), 0L);
    }
}
