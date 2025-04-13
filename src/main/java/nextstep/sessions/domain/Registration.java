package nextstep.sessions.domain;

import nextstep.users.domain.NsUser;

import java.util.HashSet;
import java.util.Set;

public class Registration {
    private final RegistrationType registrationType;
    private final int maxStudentNumber;
    private final Set<NsUser> registeredUsers;

    public Registration(RegistrationType registrationType) {
        this(registrationType, Integer.MAX_VALUE);
    }

    public Registration(RegistrationType registrationType, int maxStudentNumber) {
        if (maxStudentNumber == Integer.MAX_VALUE && registrationType != RegistrationType.FREE) {
            throw new IllegalArgumentException();
        }
        this.registrationType = registrationType;
        this.maxStudentNumber = maxStudentNumber;
        this.registeredUsers = new HashSet<>();
    }

    public void register(NsUser user) {
        if (registeredUsers.size() == maxStudentNumber) {
            throw new IllegalStateException();
        }
        if (registeredUsers.contains(user)) {
            return;
        }
        registeredUsers.add(user);
    }
}
