package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public interface PayStrategy {
    public void pay(NsUser user, Long sessionId, int studentCount, NaturalNumber money);
}
