package nextstep.session.domain.property;

import nextstep.session.domain.constraint.SessionConstraint;

public interface SessionTypeEnrollStrategy {
    boolean canEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount);
}
