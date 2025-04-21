package nextstep.courses.domain;

import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Component;

@Component
public class FreePayStrategy implements PayStrategy {
    @Override
    public void pay(NsUser user, Long sessionId, PositiveNumber money, PositiveNumber price) {
        // pass
    }

    @Override
    public String getType() {
        return "FREE";
    }
}
