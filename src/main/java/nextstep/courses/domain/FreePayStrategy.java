package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class FreePayStrategy implements PayStrategy {
    @Override
    public void pay(NsUser user, Long sessionId, int studentCount, NaturalNumber money) {
        // pass
    }
}
