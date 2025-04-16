package nextstep.users.domain;

import java.util.ArrayList;
import java.util.List;

public class NsUsers {
    List<NsUser> nsUsers = new ArrayList<>();

    public void add(NsUser user) {
        nsUsers.add(user);
    }
}
