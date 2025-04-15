package nextstep.courses.domain.session.property;

import nextstep.courses.domain.session.constraint.SessionConstraint;

public interface SessionTypeEnrollStrategy {
    boolean canEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount);
}
