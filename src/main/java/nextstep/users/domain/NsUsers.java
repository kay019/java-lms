package nextstep.users.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NsUsers {

    private final List<NsUser> nsUsers;

    public NsUsers() {
        this(new ArrayList<>());
    }

    public NsUsers(List<NsUser> nsUsers) {
        this.nsUsers = nsUsers;
    }

    public List<NsUser> getNsUsers() {
        return nsUsers;
    }

    public void add(NsUser nsUser) {
        nsUsers.add(nsUser);
    }

    public int getSize() {
        return nsUsers.size();
    }

    public Optional<NsUser> findById(long id) {
        return nsUsers.stream()
            .filter(nsUser -> nsUser.getId().equals(id))
            .findFirst();
    }
}
