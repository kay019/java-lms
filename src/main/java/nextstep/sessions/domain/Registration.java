package nextstep.sessions.domain;

import nextstep.users.domain.NsUser;

import java.util.HashSet;
import java.util.Set;

public class Registration {
    private final RegistrationType registrationType;
    private final Long fee;
    private final int maxStudentNumber;
    private final Set<Long> registeredUserIds;

    public Registration(RegistrationType registrationType) {
        this(registrationType, 0L, Integer.MAX_VALUE);
    }

    public Registration(RegistrationType registrationType, Long fee, int maxStudentNumber) {
        if (maxStudentNumber == Integer.MAX_VALUE && registrationType != RegistrationType.FREE) {
            throw new IllegalArgumentException();
        }
        this.registrationType = registrationType;
        this.fee = fee;
        this.maxStudentNumber = maxStudentNumber;
        this.registeredUserIds = new HashSet<>();
    }

    public void register(NsUser user, Long amount) {
        if (registeredUserIds.contains(user.getId())) {
            return;
        }
        if (registeredUserIds.size() == maxStudentNumber) {
            throw new IllegalStateException();
        }
        if (registrationType == RegistrationType.PAID && !amount.equals(fee)) {
            throw new IllegalStateException();
        }
        registeredUserIds.add(user.getId());
    }
}
