package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.session.constraint.SessionConstraint;

import java.util.stream.Stream;

public enum SessionType {
    FREE((sessionConstraint, payments, payment) -> true),
    PAID((sessionConstraint, enrollmentCount, amount) ->
        sessionConstraint.isGreaterThenCapacity(enrollmentCount) && sessionConstraint.isSameFee(amount)
    );

    private final EnrollStrategy enrollStrategy;

    SessionType(EnrollStrategy enrollStrategy) {
        this.enrollStrategy = enrollStrategy;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount) {
        return enrollStrategy.canEnroll(sessionConstraint, enrollmentCount, amount);
    }

    public static SessionType fromString(String sessionType) {
        return Stream.of( SessionType.values())
            .filter(type -> type.name().equalsIgnoreCase(sessionType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown session type: " + sessionType));
    }
}
