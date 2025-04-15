package nextstep.sessions.domain;

import nextstep.users.domain.NsUser;

import java.util.HashSet;
import java.util.Set;

public class Registration {
    private final RegistrationType registrationType;
    private final Long fee;
    private final int maxStudentNumber;
    private final Set<NsUser> registeredUsers;

    public Registration(RegistrationType registrationType, Long fee, int maxStudentNumber) {
        this.registrationType = registrationType;
        this.fee = fee;
        this.maxStudentNumber = maxStudentNumber;
        this.registeredUsers = new HashSet<>();
    }

    public static Registration createFreeRegistration(RegistrationType registrationType) {
        if (!registrationType.isFree()) {
            throw new IllegalArgumentException();
        }
        return new Registration(registrationType, 0L, Integer.MAX_VALUE);
    }

    public static Registration createPaidRegistration(RegistrationType registrationType, Long fee, int maxStudentNumber) {
        if (registrationType.isFree()) {
            throw new IllegalArgumentException();
        }
        return new Registration(registrationType, fee, maxStudentNumber);
    }

    public void register(NsUser user, Long amount) {
        if (registeredUsers.contains(user)) {
            return;
        }
        checkRegister(amount);
        registeredUsers.add(user);
    }

    private void checkRegister(Long amount) {
        if (registeredUsers.size() == maxStudentNumber) {
            throw new IllegalStateException();
        }
        if (registrationType == RegistrationType.PAID && !amount.equals(fee)) {
            throw new IllegalStateException();
        }
    }

    public boolean contains(NsUser user) {
        return registeredUsers.contains(user);
    }
}
