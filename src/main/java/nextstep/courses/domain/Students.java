package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Students {
    private final List<NsUser> nsUsers = new ArrayList<>();

    public void add(NsUser nsUser) {
        this.nsUsers.add(nsUser);
    }

    public int size() {
        return nsUsers.size();
    }
}
