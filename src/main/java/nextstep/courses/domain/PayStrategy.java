package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public interface PayStrategy {
    void pay(NsUser user, Long sessionId, PositiveNumber money);
    String getType();
}
