package nextstep.sessions.domain;

import nextstep.users.domain.NsUser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Registration {
    private final Set<NsUser> registeredUsers;

    public Registration() {
        this.registeredUsers = new HashSet<>();
    }

    public Registration(Set<NsUser> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public void register(NsUser user) {
        if (registeredUsers.contains(user)) {
            return;
        }
        registeredUsers.add(user);
    }

    public int registeredUserCount() {
        return registeredUsers.size();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return Objects.equals(registeredUsers, that.registeredUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registeredUsers);
    }
}
