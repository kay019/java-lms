package nextstep.session.domain;

import java.util.Arrays;

public enum RegistrationPolicyType {

    PAID(PaidRegistrationPolicy.class) {
        @Override
        public RegistrationPolicy createPolicy(long sessionFee, int maxStudentCount) {
            return new PaidRegistrationPolicy(sessionFee, maxStudentCount);
        }
    },
    FREE(FreeRegistrationPolicy.class) {
        @Override
        public RegistrationPolicy createPolicy(long sessionFee, int maxStudentCount) {
            return new FreeRegistrationPolicy();
        }
    };

    private final Class<? extends RegistrationPolicy> clazz;

    RegistrationPolicyType(Class<? extends RegistrationPolicy> clazz) {
        this.clazz = clazz;
    }

    public static RegistrationPolicyType fromClass(Class<? extends RegistrationPolicy> clazz) {
        return Arrays.stream(RegistrationPolicyType.values())
                .filter(registrationPolicyType -> registrationPolicyType.clazz == clazz)
                .findFirst()
                .orElseThrow();
    }

    public abstract RegistrationPolicy createPolicy(long sessionFee, int maxStudentCount);

}
